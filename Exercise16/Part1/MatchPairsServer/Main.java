package MatchPairsServer;

import MatchPairsGame.IllegalDimensionsException;

/**
 * Created by Adar on 6/24/2017.
 */
public class Main {
    public static void main(String [ ] args) {

        MatchPairsServer server = new MatchPairsServer(4321);
        server.run();
    }
}
