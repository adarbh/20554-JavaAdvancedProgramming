package Part1;
import java.util.ArrayList;
import java.util.Random;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.util.stream.Stream;

/**
 * Created by Adar on 4/8/2017.
 */
public class WordDatabase {
    /**
     * This class represents a database of words
     */

    private ArrayList words;
    private ArrayList alphabet;

    /**
     * Returns an WordDatabase object that can then be used to get words
     * @return      the WordDatabase object
     */
    public WordDatabase() {
        this.words = new ArrayList();
        this.alphabet = new ArrayList();
        int k = 0;

        /* Read words from file */
        //Path file_path = Paths.get("C:\\Users\\Adar\\IdeaProjects\\20554-JavaAdvancedProgramming\\Exercise11\\Part1\\top_english_words.txt");
        String filePath = new File("").getAbsolutePath();
        filePath = filePath.concat("top_english_words.txt");
        System.out.println(filePath);
        Path file_path = Paths.get(filePath);

        try (Stream<String> lines = Files.lines(file_path)) {
            lines.forEach(s -> this.words.add(new Word(s)));
        } catch (IOException ex) {
        }

        /* Define the alphabet */
        for(int i = 0; i < 26; i++){
            this.alphabet.add(new String(new char[]{(char)(97 + (k++))}));
        }
    }

    /**
     * Returns an random Word from the database
     * @return      the Word object
     */
    public Word getWord() {
        Random randomGenerator = new Random();
        int index = randomGenerator.nextInt(this.words.size());
        return (Word) this.words.get(index);
    }

    /**
     * Returns the Alphabet used in the word database
     * @return      the alphabet in an ArrayList format
     */
    public ArrayList getAlphabet() {
        return this.alphabet;
    }

}
