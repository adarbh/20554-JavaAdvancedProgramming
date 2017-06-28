package MatchPairsGame;

import MatchPairsServer.MatchPairsServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Adar on 6/27/2017.
 */
public class MatchPairsConnection {

    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socket;

    public MatchPairsConnection(Socket socket) {
        this.socket = socket;
    }

    public void open() throws IOException {
        this.out = new ObjectOutputStream(this.socket.getOutputStream());
        this.in = new ObjectInputStream(this.socket.getInputStream());
    }

    public void close() {
        try {
            this.in.close();
            this.out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getIn() {
        return this.in;
    }

    public ObjectOutputStream getOut() {
        return this.out;
    }

    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public boolean equals(Object obj) {
        return this.socket == ((MatchPairsConnection)obj).socket;
    }
}
