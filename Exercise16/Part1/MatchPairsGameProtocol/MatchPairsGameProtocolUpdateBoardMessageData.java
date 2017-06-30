package MatchPairsGameProtocol;

import MatchPairsGame.MatchPairsBoard;

/**
 * Created by Adar on 6/29/2017.
 */
public class MatchPairsGameProtocolUpdateBoardMessageData extends MatchPairsGameProtocolMessageData {

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
