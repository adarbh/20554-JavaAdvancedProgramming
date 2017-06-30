package MessageClient;

import javax.swing.*;

/**
 * Created by Adar on 6/30/2017.
 */
public class MessageClient {
    private int port;
    private int serverPort;
    private String serverHostname;
    private JFrame frame;

    public MessageClient(int port) {
        this.port = port;
    }

    public void run() {
        /* Create the display */
        this.frame = new JFrame("Message server");
        this.frame.setSize(400, 200);
    }
}
