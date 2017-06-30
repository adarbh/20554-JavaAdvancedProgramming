package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;
import MatchPairsGame.MatchPairsBoard;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsGameSession {

    private ArrayList<MatchPairsGame.MatchPairsConnection> players;
    private int maxPlayersNum;
    private MatchPairsBoard board;
    private ArrayList<Pair> playersScore;


    public MatchPairsGameSession(int maxPlayersNum, int boardDimensions) throws IllegalDimensionsException {
        this.players = new ArrayList<MatchPairsGame.MatchPairsConnection>();
        this.playersScore = new ArrayList<Pair>();
        this.maxPlayersNum = maxPlayersNum;
        createBoard(boardDimensions);
    }

    public void increasePlayerScore(MatchPairsGame.MatchPairsConnection player) {
        for (int i = 0 ; i < this.playersScore.size() ; i++) {
            if (player.equals(this.playersScore.get(i).getKey())) {
                this.playersScore.add(new Pair(player, (int)this.playersScore.get(i).getValue() + 1));
                this.playersScore.remove(this.playersScore.get(i));
                break;
            }
         }
    }

    public int getPlayerScore(MatchPairsGame.MatchPairsConnection player) {
        for (int i = 0 ; i < this.playersScore.size() ; i++) {
            if (player.equals(this.playersScore.get(i).getKey())) {
                return (int)this.playersScore.get(i).getValue();
            }
        }

        return 0;
    }

    public boolean isPlayerTheWinner(MatchPairsGame.MatchPairsConnection player) {

        for (int i = 0 ; i < this.players.size() ; i++) {
            if (!player.equals(this.players.get(i)) && getPlayerScore(player) <= getPlayerScore(this.players.get(i))) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<Integer> getOtherPlayersScores(MatchPairsGame.MatchPairsConnection player) {

        ArrayList<Integer> otherPlayersScores = new ArrayList<Integer>();

        for (int i = 0 ; i < this.players.size() ; i++) {
            if (!player.equals(this.players.get(i))) {
                otherPlayersScores.add(getPlayerScore(this.players.get(i)));
            }
        }

        return otherPlayersScores;
    }


    public void addPlayer(MatchPairsGame.MatchPairsConnection playerSocket)
            throws GameFullException {
        if (this.isGameFull()) {
            throw new GameFullException();
        }

        this.players.add(playerSocket);
        this.playersScore.add(new Pair(playerSocket, 0));
    }

    public boolean isGameFull() {
        return this.maxPlayersNum <= this.players.size();
    }

    private void createBoard(int dimensions) throws IllegalDimensionsException {
        this.board = new MatchPairsBoard(dimensions);
    }

    public MatchPairsBoard getBoard() {
        return this.board;
    }

    public boolean isPlayerInGame(MatchPairsGame.MatchPairsConnection playerSession){
        return this.players.contains(playerSession);
    }

    public boolean isPlayerFirst(MatchPairsGame.MatchPairsConnection playerSession){
        return 0 == this.players.indexOf(playerSession);
    }

    public ArrayList<MatchPairsGame.MatchPairsConnection> getPlayers() {
        return this.players;
    }

    public boolean isGameOver() {
        return this.board.areAllCardsRevealed();
    }
}
