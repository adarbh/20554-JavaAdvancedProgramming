package MessageProtocol;

/**
 * Created by Adar on 6/30/2017.
 */
public class TextMessageData extends MessageData{
    private String text;

    public TextMessageData(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}
