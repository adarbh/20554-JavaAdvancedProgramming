package MatchPairsGameProtocol;

import java.io.Serializable;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsGameProtocolSignUpForGameMessageData implements Serializable {
    private int dimensions;

    public MatchPairsGameProtocolSignUpForGameMessageData(int dimensions) {
        this.dimensions = dimensions;
    }

    public int getDimensions() {
        return this.dimensions;
    }
}
