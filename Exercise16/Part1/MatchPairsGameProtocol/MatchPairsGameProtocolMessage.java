package MatchPairsGameProtocol;

import java.io.Serializable;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsGameProtocolMessage implements Serializable {

    public enum MatchPairsGameProtocolMessageType {
        SIGN_UP_FOR_GAME_MESSAGE
    }

    MatchPairsGameProtocolMessageType messageType;
    Object messageData;

    public MatchPairsGameProtocolMessage(MatchPairsGameProtocolMessageType messageType,
                                         Object messageData) {
        this.messageType = messageType;
        this.messageData = messageData;
    }

    public MatchPairsGameProtocolMessageType getMessageType() {
        return this.messageType;
    }

    public Object getMessageData() {
        return this.messageData;
    }
}
