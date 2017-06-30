package MessageClient;

import MessageProtocol.Message;
import MessageProtocol.TextMessageData;
import sun.awt.windows.ThemeReader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Adar on 6/30/2017.
 */
public class MessageClientInputThread extends Thread implements ActionListener {

    private JFrame frame;
    private JTextArea textArea;
    private DatagramSocket socket;

    public MessageClientInputThread(JFrame frame, JTextArea textArea, DatagramSocket socket) {
        this.frame = frame;
        this.textArea = textArea;
        this.socket = socket;
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

    public void displayTextMessage(Message message) {
        String text = ((TextMessageData)message.getMessageData()).getText();
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        this.textArea.setText(this.textArea.getText() + dateFormat.format(new Date()) + " " + text + "\n");
    }

    @Override
    public void run() {
        super.run();

        /* Create the clean button */
        JButton clearButton = new JButton("Clear");
        this.frame.add(clearButton);
        clearButton.addActionListener(this);

        /* receive text messages */
        while (true) {
            try {
                Message message = receiveMessage(this.socket);
                switch (message.getMessageType()) {
                    case TEXT_MESSAGE:
                        System.out.println("Received TEXT_MESSAGE from client");
                        displayTextMessage(message);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        this.textArea.setText("");
    }
}
