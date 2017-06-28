package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;
import MatchPairsGame.MatchPairsCard;
import MatchPairsGame.MatchPairsConnection;
import MatchPairsGameProtocol.MatchPairsGameProtocolMessage;
import MatchPairsGameProtocol.MatchPairsGameProtocolSelectCardsMessageData;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameMessageData;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsServer {

    private int port;
    private ServerSocket serverSocket;
    private boolean isStopped;
    private final int maxPlayersNum = 2;
    ArrayList<MatchPairsGameSession> games;


    public MatchPairsServer(int port) {
        this.port = port;
        this.serverSocket = null;
        this.isStopped = false;
        this.games = new ArrayList<MatchPairsGameSession>();
    }

    private void openServerSocket() {

        System.out.println("Opening server socket");

        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.port + ".", e);
        }
    }

    public void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    private void sendStartGameMessage(MatchPairsGameSession game) throws IOException {
        ArrayList<MatchPairsGame.MatchPairsConnection> players = game.getPlayers();
        ObjectOutputStream out;
        MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData messageData;

        for (int i = 0 ; i < players.size() ; i++) {
            out = players.get(i).getOut();
            messageData = new MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData(
                    game.getBoard(),
                    game.isPlayerFirst(players.get(i)));
            System.out.println("Sending SIGN_UP_FOR_GAME_APPROVAL_START_GAME_MESSAGE to client.");
            out.writeObject(new MatchPairsGameProtocolMessage(
                    MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_APPROVAL_START_GAME_MESSAGE,
                    messageData));
        }
    }

    private void sendUpdateBoardMessage(MatchPairsGameSession game,
                                        MatchPairsConnection playerSession) throws IOException {
        ArrayList<MatchPairsGame.MatchPairsConnection> players = game.getPlayers();
        ObjectOutputStream out;
        MatchPairsGameProtocol.MatchPairsGameProtocolUpdateBoardMessageData messageData;

        for (int i = 0 ; i < players.size() ; i++) {
            out = players.get(i).getOut();
            messageData = new MatchPairsGameProtocol.MatchPairsGameProtocolUpdateBoardMessageData(game.getBoard(),
                    playerSession == players.get(i));
            System.out.println("Sending UPDATE_BOARD_MESSAGE to client.");
            out.writeObject(new MatchPairsGameProtocolMessage(
                    MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.UPDATE_BOARD_MESSAGE,
                    messageData));
        }
    }

    private void handleSignUpForGameMessage(
            MatchPairsGame.MatchPairsConnection playerSession,
            MatchPairsGameProtocolSignUpForGameMessageData messageData) throws IOException {

        /* Check if there is an opening in an existing game */
        for (int i=0 ; i < this.games.size() ; i++) {
            try {
                this.games.get(i).addPlayer(playerSession, messageData.getDimensions());
                if (this.games.get(i).isGameFull()) {
                    this.sendStartGameMessage(this.games.get(i));
                } else {
                    System.out.println("Sending SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE to client.");
                    playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                            MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE,
                            null));
                }
                return;
            } catch (GameFullException| DimensionsDoNotMatchException e) {
                continue;
            } catch (IllegalDimensionsException e) {
                System.out.println("Sending ILLEGAL_DIMENSIONS_ERROR_MESSAGE to client.");
                playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                        MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.ILLEGAL_DIMENSIONS_ERROR_MESSAGE,
                        null));
            }
        }

        /* Create a new game */
        MatchPairsGameSession game = new MatchPairsGameSession(this.maxPlayersNum);
        try {
            game.addPlayer(playerSession, messageData.getDimensions());
            if (game.isGameFull()) {
                this.sendStartGameMessage(game);
            } else {
                System.out.println("Sending SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE to client.");
                playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                        MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE,
                        null));
            }
        } catch (GameFullException| DimensionsDoNotMatchException e) {
            return;
        } catch (IllegalDimensionsException e) {
            System.out.println("Sending ILLEGAL_DIMENSIONS_ERROR_MESSAGE to client.");
            playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                    MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.ILLEGAL_DIMENSIONS_ERROR_MESSAGE,
                    null));
        }
        this.games.add(game);
    }

    private MatchPairsGameSession getPlayerGame(MatchPairsConnection playerSession) {
        for (int i = 0 ; i < this.games.size() ; i++) {
            if (this.games.get(i).isPlayerInGame(playerSession)) {
                return this.games.get(i);
            }
        }

        return null;
    }

    public void handleSelectCardsMessage(MatchPairsGame.MatchPairsConnection playerSession,
                                         MatchPairsGameProtocolSelectCardsMessageData messageData) {

        boolean isSelectionLegal = true;
        boolean isSelectionCorrect = true;
        int icard= 0;
        int jcard = 0;
        MatchPairsCard card = null;

        /* Check if the selection is legal */
        if (messageData.getSelectedCards().size() != 2) {
            isSelectionLegal = false;
        }

        MatchPairsGameSession game = getPlayerGame(playerSession);
        for (int i = 0 ; i < messageData.getSelectedCards().size() ; i++) {
            icard = (int)messageData.getSelectedCards().get(i).getKey();
            jcard = (int)messageData.getSelectedCards().get(i).getValue();

            if (icard >= game.getBoard().getDimensions() || jcard >= game.getBoard().getDimensions() ||
                    icard < 0 || jcard < 0 || game.getBoard().getCard(icard, jcard).isRevealed()) {
                isSelectionLegal = false;
            }
        }

        if (!isSelectionLegal) {
            System.out.println("Sending ILLEGAL_SELECT_CARDS_ERROR_MESSAGE to client.");
            try {
                playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                        MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.ILLEGAL_SELECT_CARDS_ERROR_MESSAGE,
                        null));
            } catch (IOException e) {
                handleCommunicateErrorWithClient(playerSession);
            }
        }

        /* Send an update of the board */
        for (int i = 0 ; i < messageData.getSelectedCards().size() ; i++) {
            game.getBoard().getCard((int) messageData.getSelectedCards().get(i).getKey(),
                    (int) messageData.getSelectedCards().get(i).getValue()).reveal();
        }

        try {
            sendUpdateBoardMessage(game, playerSession);
        } catch (IOException e) {
            handleCommunicateErrorWithClient(playerSession);
        }

        /* Check if the selection is correct */
        card = game.getBoard().getCard((int) messageData.getSelectedCards().get(0).getKey(),
                (int) messageData.getSelectedCards().get(0).getValue());
        for (int i = 0 ; i < messageData.getSelectedCards().size() ; i++) {
            if (card != game.getBoard().getCard((int) messageData.getSelectedCards().get(i).getKey(),
                    (int) messageData.getSelectedCards().get(i).getValue())) {
                isSelectionCorrect = false;
            }
        }

        if (isSelectionCorrect) {
            //TODO - update player score

        } else {

            /* Let cards be visible for 3 seconds */
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0 ; i < messageData.getSelectedCards().size() ; i++) {
                game.getBoard().getCard((int) messageData.getSelectedCards().get(i).getKey(),
                        (int) messageData.getSelectedCards().get(i).getValue()).hide();
            }

            try {
                sendUpdateBoardMessage(game, playerSession);
            } catch (IOException e) {
                handleCommunicateErrorWithClient(playerSession);
            }

        }

        /* Check if the game is over */
        //TODO

    }

    private void processClientMessage(MatchPairsGame.MatchPairsConnection playerSession) throws IOException, ClassNotFoundException {

        /* Read the message */
        MatchPairsGameProtocolMessage message = (MatchPairsGameProtocolMessage) playerSession.getIn().readObject();

        /* Handle message */
        switch (message.getMessageType()) {
            case SIGN_UP_FOR_GAME_MESSAGE:
                System.out.println("Received SIGN_UP_FOR_GAME_MESSAGE from client.");
                this.handleSignUpForGameMessage(
                        playerSession,
                        (MatchPairsGameProtocolSignUpForGameMessageData) message.getMessageData());
                break;
            case SELECT_CARDS_MESSAGE:
                System.out.println("Received SELECT_CARDS_MESSAGE from client.");
                this.handleSelectCardsMessage(playerSession,
                        (MatchPairsGameProtocolSelectCardsMessageData) message.getMessageData());
            default:
                handleCommunicateErrorWithClient(playerSession);
                return;
        }

    }

    public void handleCommunicateErrorWithClient(MatchPairsGame.MatchPairsConnection playerSession) {
        System.out.println("Error communicating with client");
        try {
            playerSession.close();
            playerSession.getSocket().close();
        } catch (IOException e1) {
            return;
        }
    }

    public void run() {
        Socket clientSocket = null;
        MatchPairsGame.MatchPairsConnection playerSession;
        openServerSocket();

        /* Listen for client messages */
        while(!isStopped()){
            clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
                System.out.println("Client session was accepted");
                playerSession = new MatchPairsGame.MatchPairsConnection(clientSocket);
                playerSession.open();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                //TODO: print error and continue
                return;
            }

            System.out.println("Client streams were opened");
            try {
                processClientMessage(playerSession);
            } catch (IOException|ClassNotFoundException e) {
                handleCommunicateErrorWithClient(playerSession);
                continue;
            }
        }

        //TODO: close all connections to clients
        System.out.println("Server Stopped.");
    }
}
