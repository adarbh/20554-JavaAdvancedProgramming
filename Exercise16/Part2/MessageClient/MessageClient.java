package MessageClient;

import MessageProtocol.Message;
import MessageProtocol.RemoveFromServerMessageData;
import MessageProtocol.SignUpForServerData;
import MessageProtocol.TextMessageData;
import MessageServer.MessageServerOutputThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * Created by Adar on 6/30/2017.
 */
public class MessageClient implements ActionListener {
    private int port;
    private int serverPort;
    private String serverHostname;
    private JFrame frame;
    private JTextArea textArea;
    private JButton signUpButton;
    private JButton removeFromServerButton;
    private boolean isSignUpToServer;
    private DatagramSocket clientSocket;

    public MessageClient() {
        this.isSignUpToServer = false;
    }

    public void run() {

        /* Create the display */
        this.frame = new JFrame("Message client");
        this.frame.setSize(510, 200);
        this.frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.textArea = new JTextArea("");
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setPreferredSize(new Dimension(500, 100));
        this.frame.add(this.textArea);

        signUpButton = new JButton("Sign up to server");
        this.frame.add(signUpButton);
        signUpButton.addActionListener(this);

        removeFromServerButton = new JButton("Remove from server server");
        this.frame.add(removeFromServerButton);
        removeFromServerButton.addActionListener(this);

        this.frame.setResizable(false);

        /* Find a port and open a socket */
        this.port = getAvailablePort();
        try {
            this.clientSocket = new DatagramSocket(this.port);
        } catch (SocketException e) {
            JOptionPane.showMessageDialog(this.frame, "Failed to open client socket.");
        }

        new MessageClientInputThread(this.frame, this.textArea, this.clientSocket).start();
        this.frame.setVisible(true);
    }

    public static int getAvailablePort() {
        int minPort = 1050;
        int maxPort = 15000;
        int port;

        while(true) {
            port = minPort + (int)(Math.random() * ((maxPort - minPort) + 1));
            if (isPortAvailable(port)) {
                return port;
            }
        }
    }

    private static boolean isPortAvailable(int port) {
        try (Socket ignored = new Socket("localhost", port)) {
            return false;
        } catch (IOException ignored) {
            return true;
        }
    }

    private void sendSignUpForServerMessage() throws IOException {

        /* Create the message */
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();
        Message message = new Message(Message.MessageType.SIGN_UP_FOR_SERVER_MESSAGE,
                new SignUpForServerData(hostname, this.port));


        /* Send the message */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        oos.flush();

        // get the byte array of the object
        byte[] Buf= baos.toByteArray();

        int number = Buf.length;;
        byte[] data = new byte[4];

        // int -> byte[]
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
        }

        InetAddress server = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(data, 4, server, serverPort);
        this.clientSocket.send(packet);

        // now send the payload
        packet = new DatagramPacket(Buf, Buf.length, server, serverPort);
        this.clientSocket.send(packet);
    }

    private void sendRemoveFromServerMessage() throws IOException {
        /* Create the message */
        InetAddress addr = InetAddress.getLocalHost();
        String hostname = addr.getHostName();
        Message message = new Message(Message.MessageType.REMOVE_FOR_SERVER_MESSAGE,
                new RemoveFromServerMessageData(hostname, this.port));

        /* Send the message */
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        oos.flush();

        // get the byte array of the object
        byte[] Buf= baos.toByteArray();

        int number = Buf.length;;
        byte[] data = new byte[4];

        // int -> byte[]
        for (int i = 0; i < 4; ++i) {
            int shift = i << 3; // i * 8
            data[3-i] = (byte)((number & (0xff << shift)) >>> shift);
        }

        InetAddress server = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(data, 4, server, serverPort);
        this.clientSocket.send(packet);

        // now send the payload
        packet = new DatagramPacket(Buf, Buf.length, server, serverPort);
        this.clientSocket.send(packet);
    }

    private void removeFromServer() {
        if (!this.isSignUpToServer) {
            JOptionPane.showMessageDialog(this.frame, "You are not signed up to server.");
            return;
        }

        try {
            System.out.println("Send REMOVE_FOR_SERVER_MESSAGE to server.");
            sendRemoveFromServerMessage();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.frame, "Failed to remove from server.");
        }

        this.isSignUpToServer = false;
    }

    private void signUpForServer() {

        if (this.isSignUpToServer) {
            JOptionPane.showMessageDialog(this.frame, "You are already signed up to server.");
            return;
        }

        /* Get server details */
        serverHostname = "localhost";
        serverPort = 6666;

        /* Sign up */
        try {
            System.out.println("Send SIGN_UP_FOR_SERVER_MESSAGE to server.");
            sendSignUpForServerMessage();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.frame, "Failed signed up to server.");
        }

        this.isSignUpToServer = true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.signUpButton) {
            signUpForServer();
        } else if (e.getSource() == this.removeFromServerButton){
            removeFromServer();
        }
    }
}
