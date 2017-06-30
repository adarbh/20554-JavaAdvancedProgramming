package MessageProtocol;

/**
 * Created by Adar on 6/30/2017.
 */
public class RemoveFromServerMessageData extends MessageData {
    private String hostname;
    private int port;

    public RemoveFromServerMessageData (String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return this.hostname;
    }

    public int getPort() {
        return this.port;
    }
}
