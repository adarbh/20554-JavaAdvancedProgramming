package MessageServer;

import MatchPairsServer.MatchPairsServerHandelClientThread;
import MessageProtocol.Message;
import MessageProtocol.RemoveFromServerMessageData;
import MessageProtocol.SignUpForServerData;
import javafx.util.Pair;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import java.net.SocketException;
import java.util.ArrayList;

/**
 * Created by Adar on 6/30/2017.
 */
public class MessageServer {
    private int port;
    ArrayList<Pair> clients;

    public MessageServer(int port) {
        this.port = port;
        this.clients = new ArrayList<Pair>();
    }

    private void handleSignUpForServerMessage(Message message) {
        SignUpForServerData messageData = (SignUpForServerData)message.getMessageData();
        this.clients.add(new Pair(messageData.getHostname(), messageData.getPort()));
    }

    private void handleRemoveFromServerMessage(Message message) {
        RemoveFromServerMessageData messageData = (RemoveFromServerMessageData)message.getMessageData();
        for (int i = 0 ; i< this.clients.size() ; i++) {
            if (messageData.getHostname().equals((String) this.clients.get(i).getKey()) &&
                    messageData.getPort() == (int)this.clients.get(i).getValue()) {
                this.clients.remove(i);
            }
        }
    }

    private Message receiveMessage(DatagramSocket socket) throws IOException {
        Message message = null;

        byte[] data = new byte[4];
        DatagramPacket packet = new DatagramPacket(data, data.length );
        socket.receive(packet);

        int len = 0;
        // byte[] -> int
        for (int i = 0; i < 4; ++i) {
            len |= (data[3-i] & 0xff) << (i << 3);
        }

        // now we know the length of the payload
        byte[] buffer = new byte[len];
        packet = new DatagramPacket(buffer, buffer.length );
        socket.receive(packet);

        ByteArrayInputStream baos = new ByteArrayInputStream(buffer);
        ObjectInputStream oos = new ObjectInputStream(baos);
        try {
            message = (Message)oos.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return message;
    }

    public void run() {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(this.port);
        } catch (SocketException e) {
            System.out.println("Failed to open socket.");
            return;
        }

        new MessageServerOutputThread(this.clients, socket).start();

        while (true) {
            try {
                Message message = receiveMessage(socket);
                switch (message.getMessageType()) {
                    case SIGN_UP_FOR_SERVER_MESSAGE:
                        System.out.println("Received SIGN_UP_FOR_SERVER_MESSAGE from client");
                        handleSignUpForServerMessage(message);
                        break;
                    case REMOVE_FOR_SERVER_MESSAGE:
                        System.out.println("Received REMOVE_FOR_SERVER_MESSAGE from client");
                        handleRemoveFromServerMessage(message);
                        break;
                    default:
                        System.out.println("Unknown message was received.");
                        continue;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
