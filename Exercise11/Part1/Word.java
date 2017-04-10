package Part1;

import java.util.ArrayList;

/**
 * Created by Adar on 4/8/2017.
 */
public class Word {
    /**
     * This class represents a word
     */

    private String word;
    private String hidden_word;
    private ArrayList visible_letters;

    /**
     * Returns an Word object based on a given string representing the word
     * @param  word the word
     * @return      the Word object
     */
    public Word(String word) {
        this.word = word;
        this.visible_letters = new ArrayList();
    }

    /**
     * Checks if a given character appears in the word at least once
     * @param  c the character to check
     * @return   true if the character appears in the word, false otherwise
     */
    public boolean isCharInWord(char c) {
        return word.contains(new String(new char[] {c} ));
    }

    /**
     * Defines a given letter as visible. This means that it will appear in the hidden version of the word.
     * @param  letter the character to check
     */
    public void makeLetterVisible(char letter) {
        visible_letters.add(letter);
    }

    /**
     * Returns the hidden version of the word.
     * This is the version were every letter that have not been turned visible yet is replaced with a meaningless character
     * @return   the hidden version of the word in string format
     */
    public String getHiddenWord() {

        this.hidden_word = word;
        /* Go over the visible word and hide all not-visible letters in it */
        for (int i = 0; i < this.word.length() ; i++) {
            if (!visible_letters.contains(this.word.charAt(i))) {
                this.hidden_word = this.hidden_word.replace(this.word.charAt(i), '_');
            }
        }
        return this.hidden_word;
    }

    /**
     * Checks if the hidden version of the word is completely visible
     * @return   true if the hidden version of the word is completely visible, false otherwise
     */
    public boolean isHiddenWordVisible() {
        return this.word.equals(this.getHiddenWord());
    }

    @Override
    public String toString() {
        return word;
    }
}
