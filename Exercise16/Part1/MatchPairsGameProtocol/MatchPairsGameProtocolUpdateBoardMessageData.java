package MatchPairsGameProtocol;

import MatchPairsGame.MatchPairsBoard;

import java.io.Serializable;

/**
 * Created by Adar on 6/29/2017.
 */
public class MatchPairsGameProtocolUpdateBoardMessageData implements Serializable {

    private MatchPairsBoard board;
    private boolean isPlayerInitiated;

    public MatchPairsGameProtocolUpdateBoardMessageData(MatchPairsBoard board,
                                                        boolean isPlayerInitiated) {
        this.board = board;
        this.isPlayerInitiated = isPlayerInitiated;
    }

    public MatchPairsBoard getBoard() {
        return this.board;
    }

    public boolean isPlayerInitiated() {
        return this.isPlayerInitiated;
    }
}
