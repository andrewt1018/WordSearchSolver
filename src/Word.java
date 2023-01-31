import java.util.*;

/**
 *  WORD SEARCH PROJECT
 *  Beginning date: Oct. 27th, 2022
 *  End date:
 *
 * A word object for the purposes of creating a grid in the Gridmaker
 */
public class Word {
    private int orientation;
    private char[][] letters;

    private String word;
    public Word(String word) throws InvalidWordException {
        /**
         * Constructs a Word object given a string.
         * Checks for incorrectly formatted words (i.e. contains a special symbol or numbers)
         * Throws InvalidWordException
         */
        for (int i = 0; i < word.length(); i++) {
            if (!Character.isLetter(word.charAt(i))) {
                throw new InvalidWordException(word + " is an invalid word!");
            }
        }

        this.word = word;
        char[] letters1D = this.word.toCharArray();
        letters = new char[letters1D.length][letters1D.length];
        resetLetters();
        letters[0] = letters1D;
        orientation = 1;
    }

    public void reorient() {
        /**
         * Each orientation of the word comes in the form of an integer
         * 1: word goes straight to the right
         * 2: word goes straight to the left
         * 3: word goes upwards
         * 4: word goes downwards
         * 5: word goes diagonally up right
         * 6: word goes diagonally up left
         * 7: word goes to the bottom left
         * 8: word goes to the bottom right
         */
        switch (orientation) {
            default:
                break;
            case 1: // Right
                orientation = 1;
                resetLetters();
                for (int i = 0; i < word.length(); i++) {
                    letters[0][i] = word.charAt(i);
                }
                break;
            case 2: // Left
                orientation = 2;
                resetLetters();
                for (int i = 0; i < word.length(); i++) {
                    letters[0][i] = word.charAt(word.length() - i - 1);
                }
                break;
            case 3: // Up
                orientation = 3;
                // Renew letters[][]
                resetLetters();
                for (int i = 0; i < word.length(); i++) {
                    letters[i][0] = word.charAt(word.length() - i - 1);
                }
                break;
            case 4: // Down
                orientation = 4;
                resetLetters();
                for (int i = 0; i < word.length(); i++) {
                    letters[i][0] = word.charAt(i);
                }
                break;
            case 5: // Up right
                orientation = 5;
                resetLetters();
                for (int i = word.length() - 1; i >= 0; i--) { // Row
                    for (int j = 0; j < word.length(); j++) { // Column
                        if (i + j == word.length() - 1) letters[i][j] = word.charAt(j);
                    }
                }
                break;
            case 6: // Up left
                orientation = 6;
                resetLetters();
                for (int i = word.length() - 1; i >= 0; i--) { // Row
                    for (int j = word.length() - 1; j >= 0; j--) { // Column
                        if (i == j) letters[i][j] = word.charAt(word.length() - 1 - i);
                    }

                }
                break;
            case 7: // Down left
                orientation = 7;
                resetLetters();
                for (int i = 0; i < word.length(); i++) { // Row
                    for (int j = word.length() - 1; j >= 0; j--) { // Column
                        if (i + j == word.length() - 1) letters[i][j] = word.charAt(i);
                    }

                }
                break;
            case 8: // Down right
                orientation = 8;
                resetLetters();
                for (int i = 0; i < word.length(); i++) { // Row
                    for (int j = 0; j < word.length(); j++) { // Column
                        if (i == j) letters[i][j] = word.charAt(i);
                    }
                }
                break;
        }
    }

    public void reorient(int i) {
        /**
         * Overloads reorient()
         * Sets the orientation field as int i (iff i is a valid orientation)
         */
        if (orientation < 1 || orientation > 8) {
            System.out.println(i + " is an invalid orientation");
            return;
        }
        orientation = i;
        reorient();
    }

    private void resetLetters() {
        /**
         * Helper method where the entire char[][] array of letters is reset back to '.'
         */
        letters = new char[word.length()][word.length()]; // resize
        for (char[] row : letters) {
            Arrays.fill(row, '.');
        }
    }

    public void trim() {
        /**
         * For words with horizontal or vertical orientations only
         * Deletes all the complete columns and rows with only '.'
         * Resizes the char[][] letters array into a 2D array with only 1 row/col
         */
        char[][] temp;
        switch (orientation) {
            case 1, 2:
                temp = new char[1][word.length()];
                for (int i = 0; i < temp[0].length; i++) {
                    temp[0][i] = letters[0][i];
                }
                letters = temp;
                break;
            case 3, 4:
                temp = new char[word.length()][1];
                for (int i = 0; i < temp.length; i++) {
                    temp[i][0] = letters[i][0];
                }
                letters = temp;
                break;
        }
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public char[][] getLetters() {
        return letters;
    }

    public int getRows() {
        return letters.length;
    }

    public int getCol() {
        return letters[0].length;
    }

    public char getLetterAt(int row, int col) {
        /**
         * Returns letter at a given index
         */
        return letters[row][col];
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        /**
         * Returns the array form of the word (i.e. prints out char[][] letters)
         */
        String ret = "";
        for (int i = 0; i < letters.length; i++) {
            for (int j = 0; j < letters[0].length; j++) {
                ret += letters[i][j];
            }
            ret += "\n";
        }
        return ret;
    }
}
