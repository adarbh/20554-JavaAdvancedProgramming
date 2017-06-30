package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;
import MatchPairsGame.MatchPairsCard;
import MatchPairsGame.MatchPairsConnection;
import MatchPairsGameProtocol.MatchPairsGameProtocolMessage;
import MatchPairsGameProtocol.MatchPairsGameProtocolSelectCardsMessageData;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameMessageData;
import javafx.util.Pair;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Adar on 6/29/2017.
 */
public class MatchPairsServerHandelClientThread extends Thread {

    private MatchPairsGame.MatchPairsConnection clientConn;
    private ArrayList<MatchPairsGameSession> games;
    private int boardDimension;


    public MatchPairsServerHandelClientThread(Socket socket, ArrayList<MatchPairsGameSession> games, int boardDimension) throws IOException {
        this.clientConn = new MatchPairsGame.MatchPairsConnection(socket);
        this.games = games;
        this.boardDimension = boardDimension;
    }

    @Override
    public void run() {
        super.run();

        MatchPairsGameProtocolMessage message;

        try {
            this.clientConn.open();
        } catch (IOException e) {
            return;
        }

        while (true) {
            try {
                message = (MatchPairsGameProtocolMessage) this.clientConn.getIn().readObject();
                processClientMessage(message);
            } catch (IOException|ClassNotFoundException e) {
                handleCommunicateErrorWithClient();
                return;
            } catch (IllegalDimensionsException e) {
                //TODO
                e.printStackTrace();
            }
        }
    }

    private void sendStartGameMessage(MatchPairsGameSession game) throws IOException {
        ArrayList<MatchPairsConnection> players = game.getPlayers();
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
            out.reset();
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
            out.reset();
        }
    }

    private void handleSignUpForGameMessage(
            MatchPairsGame.MatchPairsConnection playerSession,
            MatchPairsGameProtocolSignUpForGameMessageData messageData) throws IOException, IllegalDimensionsException {

        /* Check if there is an opening in an existing game */
        for (int i=0 ; i < this.games.size() ; i++) {
            try {
                this.games.get(i).addPlayer(playerSession);
                if (this.games.get(i).isGameFull()) {
                    this.sendStartGameMessage(this.games.get(i));
                } else {
                    System.out.println("Sending SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE to client.");
                    playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                            MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE,
                            null));
                }
                return;
            } catch (GameFullException e) {
                continue;
            }
        }

        /* Create a new game */
        MatchPairsGameSession game = new MatchPairsGameSession(MatchPairsServer.maxPlayersNum, this.boardDimension);
        try {
            game.addPlayer(playerSession);
            if (game.isGameFull()) {
                this.sendStartGameMessage(game);
            } else {
                System.out.println("Sending SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE to client.");
                playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                        MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE,
                        null));
            }
        } catch (GameFullException e) {
            return;
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

    private void sendGameOverMessage(MatchPairsGameSession game,
                                        MatchPairsConnection playerSession) throws IOException {
        ArrayList<MatchPairsGame.MatchPairsConnection> players = game.getPlayers();
        ObjectOutputStream out;
        MatchPairsGameProtocol.MatchPairsGameProtocolGameOverMessageData messageData;

        for (int i = 0 ; i < players.size() ; i++) {
            out = players.get(i).getOut();
            messageData = new MatchPairsGameProtocol.MatchPairsGameProtocolGameOverMessageData(
                    game.isPlayerTheWinner(players.get(i)),
                    game.getPlayerScore(players.get(i)),
                    game.getOtherPlayersScores(players.get(i)));
            System.out.println("Sending GAME_OVER_MESSAGE to client.");
            out.writeObject(new MatchPairsGameProtocolMessage(
                    MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.GAME_OVER_MESSAGE,
                    messageData));
            out.reset();
        }
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

        Set<Pair> set = new HashSet<Pair>(messageData.getSelectedCards());
        if (set.size() != messageData.getSelectedCards().size()) {
            isSelectionLegal = false;
        }

        if (!isSelectionLegal) {
            System.out.println("Sending ILLEGAL_SELECT_CARDS_ERROR_MESSAGE to client.");
            try {
                playerSession.getOut().writeObject(new MatchPairsGameProtocolMessage(
                        MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.ILLEGAL_SELECT_CARDS_ERROR_MESSAGE,
                        null));
            } catch (IOException e) {
                handleCommunicateErrorWithClient();
            }

            return;
        }

        /* Send an update of the board */
        for (int i = 0 ; i < messageData.getSelectedCards().size() ; i++) {
            game.getBoard().getCard((int) messageData.getSelectedCards().get(i).getKey(),
                    (int) messageData.getSelectedCards().get(i).getValue()).reveal();
        }

        try {
            sendUpdateBoardMessage(game, playerSession);
        } catch (IOException e) {
            handleCommunicateErrorWithClient();
        }

        /* Check if the selection is correct */
        card = game.getBoard().getCard((int) messageData.getSelectedCards().get(0).getKey(),
                (int) messageData.getSelectedCards().get(0).getValue());
        for (int i = 0 ; i < messageData.getSelectedCards().size() ; i++) {
            if (!card.equals(game.getBoard().getCard((int) messageData.getSelectedCards().get(i).getKey(),
                    (int) messageData.getSelectedCards().get(i).getValue()))) {
                isSelectionCorrect = false;
            }
        }

        if (isSelectionCorrect) {
            game.increasePlayerScore(playerSession);
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
                handleCommunicateErrorWithClient();
            }

        }

        /* Check if the game is over */
        if (game.isGameOver()) {
            try {
                sendGameOverMessage(game, playerSession);
            } catch (IOException e) {
                handleCommunicateErrorWithClient();
            }

            this.games.remove(game);
        }
    }

    private void processClientMessage(MatchPairsGameProtocolMessage message) throws IOException, ClassNotFoundException, IllegalDimensionsException {

        /* Read the message */
        System.out.println("Received message from client.");

        /* Handle message */
        switch (message.getMessageType()) {
            case SIGN_UP_FOR_GAME_MESSAGE:
                System.out.println("Received SIGN_UP_FOR_GAME_MESSAGE from client.");
                this.handleSignUpForGameMessage(
                        this.clientConn,
                        (MatchPairsGameProtocolSignUpForGameMessageData) message.getMessageData());
                break;
            case SELECT_CARDS_MESSAGE:
                System.out.println("Received SELECT_CARDS_MESSAGE from client.");
                this.handleSelectCardsMessage(this.clientConn,
                        (MatchPairsGameProtocolSelectCardsMessageData) message.getMessageData());
                break;
            default:
                handleCommunicateErrorWithClient();
        }
    }

    public void handleCommunicateErrorWithClient() {
        System.out.println("Error communicating with client");
        try {
            this.clientConn.close();
            this.clientConn.getSocket().close();
        } catch (IOException e1) {
            return;
        }
    }
}
