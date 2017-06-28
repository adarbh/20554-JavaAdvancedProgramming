package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;
import MatchPairsGame.MatchPairsBoard;

import java.util.ArrayList;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsGameSession {

    private ArrayList<MatchPairsGame.MatchPairsConnection> players;
    private int maxPlayersNum;
    private MatchPairsBoard board;

    public MatchPairsGameSession(int maxPlayersNum) {
        this.players = new ArrayList<MatchPairsGame.MatchPairsConnection>();
        this.maxPlayersNum = maxPlayersNum;
    }

    public void addPlayer(MatchPairsGame.MatchPairsConnection playerSocket, int boardDimensions)
            throws GameFullException, IllegalDimensionsException, DimensionsDoNotMatchException, DimensionsDoNotMatchException {
        if (this.isGameFull()) {
            throw new GameFullException();
        }

        /* Check if this is the the first player to be added */
        if (0 == this.players.size()) {
            createBoard(boardDimensions);
        } else {
            if (this.board.getDimensions() != boardDimensions) {
                throw new DimensionsDoNotMatchException();
            }
        }

        this.players.add(playerSocket);
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
}
