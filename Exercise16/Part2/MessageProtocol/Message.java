package MessageProtocol;

import java.io.Serializable;

/**
 * Created by Adar on 6/30/2017.
 */
public class Message implements Serializable {

    public enum MessageType {
        SIGN_UP_FOR_SERVER_MESSAGE,
        REMOVE_FOR_SERVER_MESSAGE,
        TEXT_MESSAGE,
    }

    MessageType messageType;
    MessageData messageData;

    public Message(MessageType messageType, MessageData messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public MessageData getMessageData() {
        return this.messageData;
    }
}
