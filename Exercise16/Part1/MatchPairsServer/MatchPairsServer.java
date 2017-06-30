package MatchPairsServer;


import MatchPairsGame.IllegalDimensionsException;

import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsServer {

    private int port;
    private ServerSocket serverSocket;
    private boolean isStopped;
    public static final int maxPlayersNum = 2;
    public int boardDimension;
    private ArrayList<MatchPairsGameSession> games;


    public MatchPairsServer(int port) {
        this.port = port;
        this.serverSocket = null;
        this.isStopped = false;
        this.games = new ArrayList<MatchPairsGameSession>();
    }

    public void getBoardDimension() throws IllegalDimensionsException {
        //this.boardDimension = 4;
        Scanner in = new Scanner(System.in);
        System.out.printf("Enter the desired board dimension: ");
        this.boardDimension = in.nextInt();
        if (this.boardDimension % 2 != 0 || this.boardDimension > 10) {
            throw new IllegalDimensionsException();
        }
    }

    private void openServerSocket() {

        System.out.println("Opening server socket on port: " + this.port);

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
        try {
            getBoardDimension();
        } catch (IllegalDimensionsException e) {
            e.printStackTrace();
            return;
        }

        openServerSocket();

        /* Listen for client messages */
        while(!isStopped()){
            try {
                clientSocket = this.serverSocket.accept();
                System.out.println("Client session was accepted");
                new MatchPairsServerHandelClientThread(clientSocket, games, this.boardDimension).start();
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.");
                    return;
                }
                //TODO: print error and continue
                return;
            }

            /*
            System.out.println("Client streams were opened");
            try {
                processClientMessage(playerSession);
            } catch (IOException|ClassNotFoundException e) {
                handleCommunicateErrorWithClient(playerSession);
                continue;
            }
            */
        }

        //TODO: close all connections to clients
        System.out.println("Server Stopped.");
    }
}
