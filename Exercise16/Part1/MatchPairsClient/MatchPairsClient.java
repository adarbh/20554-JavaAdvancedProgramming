package MatchPairsClient;

import MatchPairsGameProtocol.MatchPairsGameProtocolMessage;
import MatchPairsGameProtocol.MatchPairsGameProtocolSignUpForGameMessageData;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsClient {

    private String serverHostname;
    private int serverPort;

    public void startGame() {
        Socket clientSocket = null;
        ObjectOutputStream out = null;

        try {
            clientSocket = new Socket(this.serverHostname, this.serverPort);
        } catch (IOException e) {
            // TODO
        }

        try {
            out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            //TODO
        }

        MatchPairsGameProtocolMessage message = new MatchPairsGameProtocolMessage(MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SIGN_UP_FOR_GAME_MESSAGE,
                new MatchPairsGameProtocolSignUpForGameMessageData(5));

        try {
            out.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        this.serverHostname = "localhost";
        this.serverPort = 1234;
        this.startGame();
    }
}
