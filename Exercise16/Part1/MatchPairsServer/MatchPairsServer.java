package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;
import MatchPairsGameProtocol.MatchPairsGameProtocolMessage;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameMessageData;

import java.io.ObjectInputStream;
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

    private void processClientMessage(Socket clientSocket) throws IOException, ClassNotFoundException {

        /* Read the message */
        ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
        MatchPairsGameProtocolMessage message = (MatchPairsGameProtocolMessage) in.readObject();

        /* Handle message */
        switch (message.getMessageType()) {
            case SIGN_UP_FOR_GAME_MESSAGE:
                System.out.println("Received SIGN_UP_FOR_GAME_MESSAGE from client.");
                MatchPairsGameSession game = new MatchPairsGameSession(this.maxPlayersNum);
                try {
                    game.addPlayer(clientSocket);
                } catch (GameFullException e) {
                    //TODO
                }
                try {
                    game.createBoard(((MatchPairsGameProtocolSignUpForGameMessageData)message.getMessageData()).getDimensions());
                } catch (IllegalDimensionsException e) {
                    //TODO
                }
                games.add(game);
                break;
            default:
                //TODO
                break;
        }

        in.close();
    }

    public void run() {

        Socket clientSocket = null;

        openServerSocket();

        /* Listen for client messages */
        while(!isStopped()){
            clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                throw new RuntimeException("Error accepting client connection.", e);
            }

            System.out.println("Received message from client.");
            try {
                processClientMessage(clientSocket);
            } catch (IOException e) {
                //TODO
            } catch (ClassNotFoundException e) {
                //TODO
            }
        }

        System.out.println("Server Stopped.");
    }
}
