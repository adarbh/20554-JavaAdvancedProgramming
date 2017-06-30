package MatchPairsGameProtocol;

import java.util.ArrayList;

/**
 * Created by Adar on 6/29/2017.
 */
public class MatchPairsGameProtocolGameOverMessageData extends MatchPairsGameProtocolMessageData {

    private boolean isPlayerTheWinner;
    private int playerScore;
    private ArrayList<Integer> otherPlayersScores;

    public MatchPairsGameProtocolGameOverMessageData(boolean isPlayerTheWinner, int playerScore, ArrayList<Integer> otherPlayersScores) {
        this.isPlayerTheWinner = isPlayerTheWinner;
        this.playerScore = playerScore;
        this.otherPlayersScores = otherPlayersScores;
    }

    public boolean isPlayerTheWinner() {
        return this.isPlayerTheWinner;
    }

    public ArrayList<Integer> getOtherPlayersScores() {
        return this.otherPlayersScores;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }
}
