package MessageServer;

import MessageProtocol.Message;
import MessageProtocol.TextMessageData;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by Adar on 6/30/2017.
 */
public class MessageServerOutputThread extends Thread implements ActionListener{

    private ArrayList<Pair> clients;
    private JFrame frame;
    private JTextArea textArea;
    private DatagramSocket serverSocket;

    public MessageServerOutputThread(ArrayList<Pair> clients, DatagramSocket serverSocket) {
        this.clients = clients;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        super.run();

        /* Create the display */
        this.frame = new JFrame("Message server");
        this.frame.setSize(510, 200);
        this.frame.setLayout(new FlowLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.textArea = new JTextArea("");
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setPreferredSize(new Dimension(500, 100));
        this.frame.add(this.textArea);

        JButton button = new JButton("Send!");
        this.frame.add(button);
        button.addActionListener(this);

        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    public void sendTextMessageToClient(String hostname, int port, Message message) throws IOException {
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

        InetAddress client = InetAddress.getByName(hostname);
        DatagramPacket packet = new DatagramPacket(data, 4, client, port);
        serverSocket.send(packet);

        // now send the payload
        packet = new DatagramPacket(Buf, Buf.length, client, port);
        serverSocket.send(packet);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /* Create the message */
        Message message = new Message(Message.MessageType.TEXT_MESSAGE,
                new TextMessageData(this.textArea.getText()));

        /* Send message to all clients */
        for (int i = 0 ; i < this.clients.size() ; i++) {
            try {
                System.out.println("Sending TEXT_MESSAGE to client");
                sendTextMessageToClient((String)this.clients.get(i).getKey(),
                        (int)this.clients.get(i).getValue(), message);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        /* Clean up text area */
        this.textArea.setText("");
    }
}
