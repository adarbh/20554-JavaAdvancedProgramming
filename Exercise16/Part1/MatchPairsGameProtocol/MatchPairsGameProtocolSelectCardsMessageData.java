package MatchPairsGameProtocol;

import java.io.Serializable;
import java.util.ArrayList;
import java.awt.*;


import javafx.util.Pair;

/**
 * Created by Adar on 6/28/2017.
 */
public class MatchPairsGameProtocolSelectCardsMessageData extends MatchPairsGameProtocolMessageData {
    private ArrayList<Pair> selectedCards;

    public MatchPairsGameProtocolSelectCardsMessageData(ArrayList<Pair> selectedCards) {
        this.selectedCards = selectedCards;
    }

    public ArrayList<Pair> getSelectedCards() {
        return this.selectedCards;
    }
}
