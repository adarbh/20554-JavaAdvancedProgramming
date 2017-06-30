package MatchPairsGameProtocol;

import MatchPairsGame.MatchPairsBoard;

import java.io.Serializable;

/**
 * Created by Adar on 6/26/2017.
 */
public class MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData extends MatchPairsGameProtocolMessageData {

    private MatchPairsBoard board;
    private boolean isPlayerFirst;

    public MatchPairsGameProtocolSignUpForGameApprovalStartGameMessageData(
            MatchPairsBoard board,
            boolean isPlayerFirst) {
        this.board = board;
        this.isPlayerFirst = isPlayerFirst;
    }

    public MatchPairsBoard getBoard() {
        return this.board;
    }

    public boolean isPlayerFirst() {
        return this.isPlayerFirst;
    }
}
