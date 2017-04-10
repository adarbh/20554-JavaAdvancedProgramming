package Part1;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.*;

/**
 * Created by Adar on 4/8/2017.
 */
public class WordGuessGame {
    /**
     * This class represents a word guess game
     */

    private WordDatabase word_database;
    private JFrame frame;
    private String game_title;

    /**
     * Returns an WordGuessGame object with an initialized word database
     * @return      the WordGuessGame object
     */
    public WordGuessGame() {
        /* Create the word database */
        this.word_database = new WordDatabase();
        this.game_title = "Word Guess Game";
    }

    /**
     * Starts a guess of a letter in a game by displaying the options to the user and receiving the chosen letter
     * @param  word the word chosen for the game
     * @param  possibleValues the optional characters for the user to choose from
     * @return   true if the user did not want to quit the game, false otherwise
     */
    private boolean startGuess(Word word, ArrayList possibleValues) {

        Object[] possibleValuesArray = possibleValues.toArray();

        /* Display the work and let the user choose a letter */
        Object selectedValue = JOptionPane.showInputDialog(this.frame,
                "The hidden word is:\n" + word.getHiddenWord().replace("", " ").trim() + "\nChoose a letter to guess:",
                this.game_title, JOptionPane.PLAIN_MESSAGE, null,
                possibleValuesArray, possibleValuesArray[0]);

        if (null == selectedValue) {
            return false;
        }

        /* Check the received choice */
        possibleValues.remove(selectedValue);
        if (word.isCharInWord(((String)selectedValue).charAt(0))) {
            JOptionPane.showMessageDialog(this.frame, "Congratulations! You have chosen a letter found in the word.");
            word.makeLetterVisible(((String)selectedValue).charAt(0));
        } else {
            JOptionPane.showMessageDialog(this.frame, "Wrong! You have chosen a letter not found in the word.");
        }

        return true;
    }

    /**
     * Starts a turn of the game which is composed of guesses given to the user until he wins or quits
     */
    private void startTurn() {

        boolean is_game_on = true;
        int guess_counter = 0;
        boolean is_guess_needed = true;

        /* Get the word and the alphabet */
        Word word = this.word_database.getWord();
        ArrayList possibleValues = this.word_database.getAlphabet();

        /* Start the guesses */
        while (is_guess_needed) {
            is_game_on = startGuess(word, possibleValues);
            if (!is_game_on) {
                return;
            }

            guess_counter ++;

            /* Check if the user won */
            if (word.isHiddenWordVisible()) {
                JOptionPane.showMessageDialog(this.frame, "Congratulations! You have guessed the word!\nIt is: " + word.toString() + "!\n Number of guesses needed: " + guess_counter);
                is_guess_needed = false;
            }
        }
    }

    /**
     * Starts the game. The game is composed of turns when in any stage the user can quit
     */
    public void startGame() {

        /* Initialize the game display */
        this.frame = new JFrame(this.game_title);
        int start_turn = JOptionPane.YES_OPTION;

        /* Start playing */
        while (JOptionPane.YES_OPTION == start_turn) {
            this.startTurn();
            start_turn = JOptionPane.showConfirmDialog(null,
                    "Do you want to start another game?",
                    this.game_title,
                    JOptionPane.YES_NO_OPTION);
        }

        /* Close the frame */
        this.frame.setVisible(false);
        this.frame.dispose();
    }
}
