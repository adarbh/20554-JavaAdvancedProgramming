package MatchPairsClient;

import MatchPairsGame.MatchPairsBoard;
import MatchPairsGame.MatchPairsBoardJPanel;
import MatchPairsGameProtocol.MatchPairsGameProtocolMessage;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameMessageData;
import MatchPairsGame.MatchPairsConnection;
import MatchPairsGameProtocol.MatchPairsGameProtocolUpdateBoardMessageData;

import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsClient {

    private String serverHostname;
    private int serverPort;
    private int boardDimensions;
    private JFrame frame;
    private String gameTitle;
    private int frameDimensions;
    MatchPairsBoardJPanel panel;

    public MatchPairsClient () {this.gameTitle = "Match Pairs Game";}

    private void getServerDetailsFromPlayer() {
        this.serverHostname = "localhost";
        this.serverPort = 4321;
        this.frameDimensions = 0;
        //TODO
    }

    private void getBoardDimensionsFromPlayer() {
        /* Check with max num of colors */
        this.boardDimensions = 4;
        //TODO
    }

    private void displayBoard(MatchPairsBoard board, boolean isPlayerFirst, MatchPairsConnection playerSession) {
        //MatchPairsBoardJPanel panel = null;

        frameDimensions = boardDimensions * 50;
        this.frame.setSize(frameDimensions, frameDimensions);
        panel = new MatchPairsBoardJPanel(board, frameDimensions, isPlayerFirst, playerSession);
        //panel.repaint();
        this.frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private void handleSignUpForGameApprovalStartGameMessage (MatchPairsGameProtocolMessage message,
                                                              MatchPairsConnection playerSession) {

        MatchPairsBoard board = ((MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData) message.getMessageData()).getBoard();
        boolean isPlayerFirst = ((MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData) message.getMessageData()).isPlayerFirst();
        boolean isGameOver = false;

        if (isPlayerFirst) {
            JOptionPane.showMessageDialog(this.frame, "The game has started, it is your turn. Please choose two cards.");
        } else {
            JOptionPane.showMessageDialog(this.frame, "The game has started, it is not your turn. Please wait.");
        }

        displayBoard(board, isPlayerFirst, playerSession);

        /* Wait for end of game message or a board update */
        while (!isGameOver) {
            try {
                message = (MatchPairsGameProtocolMessage) playerSession.getIn().readObject();
            } catch (IOException | ClassNotFoundException e) {
                handleCommunicateErrorWithServer(playerSession);
            }

            switch (message.getMessageType()) {
                case ILLEGAL_SELECT_CARDS_ERROR_MESSAGE:
                    System.out.println("Received ILLEGAL_SELECT_CARDS_ERROR_MESSAGE from server.");
                    JOptionPane.showMessageDialog(this.frame, "Illegal cards where selected");
                case UPDATE_BOARD_MESSAGE:
                    System.out.println("Received UPDATE_BOARD_MESSAGE from server.");
                    MatchPairsGameProtocolUpdateBoardMessageData messageData =
                            (MatchPairsGameProtocolUpdateBoardMessageData) message.getMessageData();
                    panel.setBoard(messageData.getBoard());
                    panel.setIsPlayerTurn(!messageData.isPlayerInitiated());
                case GAME_OVER_MESSAGE:
                    System.out.println("Received GAME_OVER_MESSAGE from server.");
                    //handleSignUpForGameApprovalStartGameMessage(message, playerSession);
                    isGameOver = true;
                    break;
                default:
                    handleCommunicateErrorWithServer(playerSession);
                    break;
            }
        }

    }

    private void handleSignUpForGameApprovalWaitForPlayerMessage(MatchPairsConnection playerSession) {
        MatchPairsGameProtocolMessage message = null;

        try {
            message = (MatchPairsGameProtocolMessage)playerSession.getIn().readObject();
        } catch (IOException|ClassNotFoundException e) {
            e.printStackTrace();
            handleCommunicateErrorWithServer(playerSession);
            return;
        }
        if (message.getMessageType() !=
                MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_APPROVAL_START_GAME_MESSAGE) {
            handleCommunicateErrorWithServer(playerSession);
            return;
        }

        System.out.println("Received SIGN_UP_FOR_GAME_APPROVAL_START_GAME_MESSAGE from server.");
        handleSignUpForGameApprovalStartGameMessage(message, playerSession);
    }

    private void handleCommunicateErrorWithServer(MatchPairsConnection playerSession) {
        JOptionPane.showMessageDialog(this.frame, "Error communicating with server");
        try {
            playerSession.close();
            playerSession.getSocket().close();
        } catch (IOException e1) {
            return;
        }
    }

    public void startGame() {
        Socket clientSocket = null;
        MatchPairsConnection playerSession;

        System.out.println("Stating a new game");
        getServerDetailsFromPlayer();
        getBoardDimensionsFromPlayer();



        /* Open connection to server */
        try {
            clientSocket = new Socket(this.serverHostname, this.serverPort);
            playerSession = new MatchPairsConnection(clientSocket);
            playerSession.open();
            System.out.println("Sending SIGN_UP_FOR_GAME_MESSAGE to server.");
        } catch (IOException e) {
            // TODO
            e.printStackTrace();
            return;
        }



        /* Send a request to sign in to a game */
        MatchPairsGameProtocolMessage message = new MatchPairsGameProtocolMessage(
                MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_MESSAGE,
                new MatchPairsGameProtocolSignUpForGameMessageData(this.boardDimensions));
        try {
            System.out.println("Sending SIGN_UP_FOR_GAME_MESSAGE to server.");
            playerSession.getOut().writeObject(message);
            message = (MatchPairsGameProtocolMessage)playerSession.getIn().readObject();
        } catch (IOException|ClassNotFoundException e) {
            handleCommunicateErrorWithServer(playerSession);
        }

        /* Wait for the game to start */
        switch (message.getMessageType()) {
            case ILLEGAL_DIMENSIONS_ERROR_MESSAGE:
                System.out.println("Received ILLEGAL_DIMENSIONS_ERROR_MESSAGE from server.");
                JOptionPane.showMessageDialog(this.frame, "Illegal dimensions");
                return;
            case SIGN_UP_FOR_GAME_APPROVAL_START_GAME_MESSAGE:
                System.out.println("Received SIGN_UP_FOR_GAME_APPROVAL_START_GAME_MESSAGE from server.");
                handleSignUpForGameApprovalStartGameMessage(message, playerSession);
                break;
            case SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE:
                System.out.println("Received SIGN_UP_FOR_GAME_APPROVAL_WAIT_FOR_PLAYERS_MESSAGE from server.");
                JOptionPane.showMessageDialog(this.frame, "Sign up for game successfully, waiting for other players");
                handleSignUpForGameApprovalWaitForPlayerMessage(playerSession);
                break;
            default:
                break;
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            return;
        }
    }

    public void run() {

        this.frame = new JFrame(this.gameTitle);
        int startGame = JOptionPane.YES_OPTION;

        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        /* Start playing */
        while (JOptionPane.YES_OPTION == startGame) {
            this.startGame();
            startGame = JOptionPane.showConfirmDialog(null,
                    "Do you want to start another game?",
                    this.gameTitle,
                    JOptionPane.YES_NO_OPTION);
        }

        /* Close the frame */
        frame.dispose();
    }
}
