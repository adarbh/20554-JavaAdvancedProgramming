package MessageServer;

/**
 * Created by Adar on 6/30/2017.
 */
public class Main {
    public static void main(String [ ] args) {

        MessageServer messageClient = new MessageServer(6666);
        messageClient.run();
    }
}
