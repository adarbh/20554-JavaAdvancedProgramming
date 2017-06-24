package MatchPairsGame;

/**
 * Created by Adar on 6/24/2017.
 */
public class MatchPairsCard {
    
    private boolean isRevealed;
    private int Id;
    
    public MatchPairsCard(int Id) {
        this.isRevealed = false;
        this.Id = Id;
    }

    public void reveal() {
        this.isRevealed = true;
    }

    public void hide() {
        this.isRevealed = false;
    }

    public int getPictureId() {
        return this.Id;
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    @Override
    public boolean equals(Object obj) {
        MatchPairsCard card;
        card = (MatchPairsCard)obj;

        return card.getPictureId() == this.getPictureId();
    }
}
