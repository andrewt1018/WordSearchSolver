import java.util.*;

/**
 *  WORD SEARCH PROJECT
 *  Beginning date: Oct. 27th, 2022
 *  End date:
 *
 * The object is a grid of characters for the wordsearch game
 * Parameters for the constructor is an arraylist of strings that should appear in the grid.
 *
 * Rules:
 * 1. Words can be oriented however they want
 * 2. Words cannot overlap with other words for more than 2 spaces
 * 3. Words cannot contain spaces
 *
 */
public class Gridmaker {
    private ArrayList<Word> words; // String of words used in the game
    private ArrayList<String> inputs;
    private char[][] grid;
    private int size; // Size of the square (size x size)

    public Gridmaker(ArrayList<String> inputs) {
        /**
         * Constructs a grid with a grid attribute and a words attribute for the Word Search game
         */
        this.words = new ArrayList<>();
        ArrayList<String> removeWords = new ArrayList<>();
        for (String word : inputs) {
            word = word.toLowerCase();
            Word w;
            try {
                w = new Word(word);
                words.add(w);
            } catch(InvalidWordException iwe) {
                System.out.println(iwe.getMessage());
                removeWords.add(word);
            }
        }
        for (String word : removeWords) {
            inputs.remove(word);
        }
        this.inputs = inputs;

        size = (words.size() / 3) * 2; // 27 words --> square with length of 18

        // Finds the length of the longest word in words
        int maxLength = Integer.MIN_VALUE;
        for (String word : inputs) {
            if (word.length() >= maxLength) maxLength = word.length();
        }

        // Sets a minimum size for the grid
        if (size < 10) {
            size = 10;
        }
        if (size < maxLength) {
            size = maxLength + 1;
        }

        grid = new char[size][size];
        for (char[] row : grid) {
            Arrays.fill(row, '.');
        }

        createGrid();
    }


    public void createGrid() {
        /**
         * Creates a grid for the game
         *
         * Directly changes the grid field for this class
         */
        // Placing each word into the grid
        for (Word word : words) {
            grid = placeWord(word);
        }
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                if (grid[r][c] == '.') {
                    grid = fillInLetter(r, c);
                }
            }
        }
    }

    private char[][] placeWord(Word word) {
        /**
         * Helper method for createGrid()
         *
         * Places given word into the grid field
         * Does so recursively if given word violates the rules
         *
         * returns a grid with the given word placed inside at a random location in a random orientation
         */
        // Create a new char[][] grid in case word cannot be placed, then return this char[][] grid
        // Copies each value of grid to result because original values might change
        char[][] result = new char[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                result[row][col] = grid[row][col];
            }
        }

        // Prepare the word for placement
        Random rand = new Random();
        word.setOrientation(rand.nextInt(8) + 1);
        word.reorient();
        word.trim();

        int rows = word.getRows();
        int cols = word.getCol();

        // Find the starting index to place the word
        int beginRow = rand.nextInt(result.length - rows);
        int beginCol = rand.nextInt(result[0].length - cols);

        // Place each character in char[][] letters of Word word into the grid
        boolean validPlace = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (result[beginRow + i][beginCol + j] == word.getLetterAt(i, j)) {
                    result[beginRow + i][beginCol + j] = word.getLetterAt(i, j);
                } else if (result[beginRow + i][beginCol + j] == '.'){
                    result[beginRow + i][beginCol + j] = word.getLetterAt(i, j);
                } else {
                    validPlace = false;
                    break;
                }
            }
            if (!validPlace) break;
        }

        if (!validPlace) {
            result = placeWord(word); // if placement is not valid, then re-place the word
        }
        return result;
    }

    public char[][] fillInLetter(int row, int col) {
        /**
         * Helper method for the method createGrid()
         *
         * Given a specific index for the grid, fill in a random letter at that place and
         * ensure it doesn't form new words from the list of words
         *
         * all lower case letters have range from 97 to 122 (inclusive a-z)
         *
         * returns a grid with a new letter filled in at the given position
         */
        // Create a new char[][] grid in case word cannot be placed, then return this char[][] grid
        // Copies each value of grid to result because original values might change
        char[][] result = new char[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                result[r][c] = grid[r][c];
            }
        }

        Random rand = new Random();
        char fill = (char) (rand.nextInt(26) + 97);
        result[row][col] = fill;

        ArrayList<String> testStrings = new ArrayList<>();
        String leftRight;
        String rightLeft;
        String downUp;
        String upDown;
        String toUpRight;
        String toUpLeft;
        String toBottomLeft;
        String toBottomRight;
        if (row == 0 && col == 0) { // If character is top left corner
            leftRight = Character.toString(result[row][col]) + result[row][col + 1];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row + 1][col]) + result[row][col];
            upDown = reverseString(downUp);
            toUpRight = "";
            toUpLeft = Character.toString(result[row + 1][col + 1]) + result[row][col];
            toBottomLeft = "";
            toBottomRight = reverseString(toUpLeft);
        } else if (row == 0 && col == grid[0].length - 1) { // If character is top right corner
            leftRight = Character.toString(result[row][col - 1]) + result[row][col];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row + 1][col]) + result[row][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row + 1][col - 1]) + result[row][col];
            toUpLeft = "";
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = "";
        } else if (row == grid.length - 1 && col == 0) { // If character is bottom left corner
            leftRight = Character.toString(result[row][col + 1]) + result[row][col];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row][col]) + result[row - 1][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row][col]) + result[row - 1][col + 1];
            toUpLeft = "";
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = "";
        } else if (row == grid.length - 1 && col == grid[0].length - 1) { // If character is bottom right corner
            leftRight = Character.toString(result[row][col - 1]) + result[row][col];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row][col]) + result[row - 1][col];
            upDown = reverseString(downUp);
            toUpRight = "";
            toUpLeft = Character.toString(result[row][col]) + result[row - 1][col - 1];
            toBottomLeft = "";
            toBottomRight = reverseString(toUpLeft);
        } else if (row == 0) { // If the character is not in a corner but on the top edge
            leftRight = Character.toString(result[row][col - 1]) + result[row][col] + result[row][col + 1];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row + 1][col]) + result[row][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row + 1][col - 1]) + result[row][col];
            toUpLeft = Character.toString(result[row + 1][col + 1]) + result[row][col];
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = reverseString(toUpLeft);
        } else if (col == 0) { // If the character is not in a corner but on the left edge
            leftRight = Character.toString(result[row][col + 1]) + result[row][col];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row + 1][col]) + result[row][col] + result[row - 1][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row][col]) + result[row - 1][col + 1];
            toUpLeft = Character.toString(result[row + 1][col + 1]) + result[row][col];
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = reverseString(toUpLeft);
        } else if (row == grid.length - 1) { // If the character is not in a corner but on the bottom edge
            leftRight = Character.toString(result[row][col - 1]) + result[row][col] + result[row][col + 1];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row][col]) + result[row - 1][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row][col]) + result[row - 1][col + 1];
            toUpLeft = Character.toString(result[row][col]) + result[row - 1][col - 1];
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = reverseString(toUpLeft);
        } else if (col == grid[0].length - 1) { // If the character is not in a corner but on the right edge
            leftRight = Character.toString(result[row][col - 1]) + result[row][col];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row + 1][col]) + result[row][col] + result[row - 1][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row + 1][col - 1]) + result[row][col];
            toUpLeft = Character.toString(result[row][col]) + result[row - 1][col - 1];
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = reverseString(toUpLeft);
        } else {
            leftRight = Character.toString(result[row][col - 1]) + result[row][col] + result[row][col + 1];
            rightLeft = reverseString(leftRight);
            downUp = Character.toString(result[row + 1][col]) + result[row][col] + result[row - 1][col];
            upDown = reverseString(downUp);
            toUpRight = Character.toString(result[row + 1][col - 1]) + result[row][col] + result[row - 1][col + 1];
            toUpLeft = Character.toString(result[row + 1][col + 1]) + result[row][col] + result[row - 1][col - 1];
            toBottomLeft = reverseString(toUpRight);
            toBottomRight = reverseString(toUpLeft);
        }

        testStrings.add(leftRight);
        testStrings.add(rightLeft);
        testStrings.add(downUp);
        testStrings.add(upDown);
        testStrings.add(toUpRight);
        testStrings.add(toUpLeft);
        testStrings.add(toBottomLeft);
        testStrings.add(toBottomRight);

        boolean valid = true;
        for (String word : inputs) {
            int orientation = 1;
            for (String test : testStrings) {
                if (word.contains(test)) {
                    // valid = expandedSearch(row, col, orientation, word, result);
                    valid = false;
                    if (!valid) break;
                }
                orientation += 1;
            }
            if (!valid) break;
        }
        if (!valid)
            result = fillInLetter(row, col);

        return result;


    }

    private boolean expandedSearch(int row, int col, int orientation, String word, char[][] tempGrid) {
        /**
         * Helper method for fillInLetter()
         *
         * When filling in a random letter at a given position, if the letter is suspected to
         * form new instances of words from the words-to-be-found, an in depth search is conducted
         * to ensure that no words, in all 8 directions, clash with any of the words from the words-to-be-found
         *
         * Return false if a clash is found
         * Return true if no clashes are found
         */
        boolean valid = true;
        int r;
        int c;
        switch (orientation) {
            case 1, 2: // Check the entire horizontal row, both directions
                String testRow = "";
                for (int co = 0;  co < tempGrid[0].length; co++) {
                    testRow += Character.toString(tempGrid[row][co]);
                }
                if (testRow.contains(word) &&
                        (testRow.indexOf(word) <= col && (col <= testRow.indexOf(word) + word.length()))) {
                    // If the added character is within the testRow
                    valid = false;
                }
                testRow = reverseString(testRow);
                if (testRow.contains(word) &&
                        (testRow.indexOf(word) <= col && (col <= testRow.indexOf(word) + word.length()))) {
                    // If the added character is within the reversed testRow
                    valid = false;
                }
                break;
            case 3, 4: // Check the entire vertical column
                String testCol = "";
                for (int ro = 0; ro < tempGrid.length; ro++) {
                    testCol += Character.toString(tempGrid[ro][col]);
                }
                if (testCol.contains(word) &&
                        (testCol.indexOf(word) <= row && (row <= testCol.indexOf(word) + word.length()))) {
                    // If the added character is within the testCol
                    valid = false;
                }
                testCol = reverseString(testCol);
                if (testCol.contains(word) &&
                        (testCol.indexOf(word) <= row && (row <= testCol.indexOf(word) + word.length()))) {
                    // If the added character is within the reversed testCol
                    valid = false;
                }
                break;
            case 5, 7: // Check the diagonal going from the bottom left to the top right
                String testRightDiag = "";
                String testRightDiagRight = "";
                String testRightDiagLeft = "";
                r = row;
                c = col;
                while (r >= 0 && c < tempGrid[0].length) {
                    testRightDiagRight += Character.toString(tempGrid[r][c]);
                    c++;
                    r--;
                }
                r = row;
                c = col;
                while (r < tempGrid.length && c >= 0) {
                    testRightDiagLeft += Character.toString(tempGrid[r][c]);
                    c--;
                    r++;
                }
                if (testRightDiagLeft.length() == 1) {
                    testRightDiag = testRightDiagRight;
                } else {
                    testRightDiagLeft = testRightDiagLeft.substring(1);
                    testRightDiag = reverseString(testRightDiagLeft) + testRightDiagRight;
                }

                if (testRightDiag.contains(word) &&
                        testRightDiag.indexOf(word) >= row && testRightDiag.indexOf(word) + word.length() <= row) {
                    if (testRightDiag.indexOf(word) <= col && testRightDiag.indexOf(word) + word.length() >= col) {
                        valid = false;
                    }
                }
                testRightDiag = reverseString(testRightDiagRight);
                if (testRightDiag.contains(word) &&
                        testRightDiag.indexOf(word) >= row && testRightDiag.indexOf(word) + word.length() <= row) {
                    if (testRightDiag.indexOf(word) <= col && testRightDiag.indexOf(word) + word.length() >= col) {
                        valid = false;
                    }
                }
                break;
            case 6, 8: // Check the diagonal going from the top left to the bottom right
                String testLeftDiag = "";
                String testLeftDiagRight = "";
                String testLeftDiagLeft = "";
                r = row;
                c = col;
                while (r < tempGrid.length && c < tempGrid[0].length) {
                    testLeftDiagRight += Character.toString(tempGrid[r][c]);
                    c++;
                    r++;
                }
                r = row;
                c = col;
                while (r >= 0 && c >= 0) {
                    testLeftDiagLeft += Character.toString(tempGrid[r][c]);
                    c--;
                    r--;
                }
                if (testLeftDiagLeft.length() == 1) {
                    testLeftDiag = testLeftDiagRight;
                } else {
                    testLeftDiagLeft = testLeftDiagLeft.substring(1);
                    testLeftDiag = reverseString(testLeftDiagLeft) + testLeftDiagRight;
                }

                if (testLeftDiag.contains(word) &&
                        testLeftDiag.indexOf(word) <= row && testLeftDiag.indexOf(word) + word.length() >= row) {
                    if (testLeftDiag.indexOf(word) <= col && testLeftDiag.indexOf(word) + word.length() >= col) {
                        valid = false;
                    }
                }
                testLeftDiag = reverseString(testLeftDiagRight);
                if (testLeftDiag.contains(word) &&
                        testLeftDiag.indexOf(word) <= row && testLeftDiag.indexOf(word) + word.length() >= row) {
                    if (testLeftDiag.indexOf(word) <= col && testLeftDiag.indexOf(word) + word.length() >= col) {
                        valid = false;
                    }
                }
                break;
        }
        return valid;
    }

    public static String reverseString(String s) {
        /**
         * Helper method for many other methods such as expandedSearch(), fillInletter() etc.
         *
         * Given a string, returns the string in reverse
         */
        char[] chars = s.toCharArray();
        char temp;
        int begin = 0;
        int end = chars.length - 1;
        while (end > begin) {
            temp = chars[begin];
            chars[begin] = chars[end];
            chars[end] = temp;
            begin++;
            end--;
        }
        return String.valueOf(chars);
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public char[][] getGrid() {
        return grid;
    }

    @Override
    public String toString() {
        /**
         * Returns the string form of the grid field with a new line after each row, in a square fashion
         *
         * Appends the words needed to be found at the end
         */
        String ret = "";
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                ret += grid[i][j] + "  ";
            }
            ret += "\n";
        }
        for (String word : inputs) {
            ret += word + "  ";
        }
        return ret;
    }
}
