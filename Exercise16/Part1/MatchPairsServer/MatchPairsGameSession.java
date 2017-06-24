package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;
import MatchPairsGame.MatchPairsBoard;

import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsGameSession {

    private ArrayList<Socket> players;
    private int maxPlayersNum;
    private MatchPairsBoard board;

    public MatchPairsGameSession(int maxPlayersNum) {
        this.players = new ArrayList<Socket>();
        this.maxPlayersNum = maxPlayersNum;
    }

    public void addPlayer(Socket playerSocket) throws GameFullException {
        if (this.isGameFull()) {
            throw new GameFullException();
        }

        this.players.add(playerSocket);
    }

    public boolean isGameFull() {
        return this.maxPlayersNum <= this.players.size();
    }

    public void createBoard(int dimensions) throws IllegalDimensionsException {
        this.board = new MatchPairsBoard(dimensions);
    }

    public MatchPairsBoard getBoard() {
        return this.board;
    }

    public boolean isPlayerInGame(Socket playerSocket){
        return this.players.contains(playerSocket);
    }
}
