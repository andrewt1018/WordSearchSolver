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
    private static int placeWordCounter = 0;
    private ArrayList<Word> words; // String of words used in the game
    private ArrayList<String> inputs;
    private ArrayList<String> unplacedWords;
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
        this.unplacedWords = new ArrayList<>();

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
            placeWordCounter = 0;
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
        placeWordCounter++;
        char[][] result = new char[grid.length][grid[0].length];
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                result[row][col] = grid[row][col];
            }
        }
        if (placeWordCounter == 500) {
            unplacedWords.add(word.getWord());
            return result;
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


        boolean valid = true;
        for (Word word : words) {
            String currentWord = word.getWord();
            // Checking horizontally
            // If this row contains word, and if original grid does not contain word, valid = false
            if (result[row].toString().contains(currentWord)) {
                int indexOfWord = result[row].toString().indexOf(currentWord, col - currentWord.length());
                if (indexOfWord == col ||
                    indexOfWord == col - currentWord.length() + 1) {
                    valid = false;
                    break;
                }
            } else if (result[row].toString().contains(reverseString(currentWord))) {
                int indexOfWord = result[row].toString().indexOf(reverseString(currentWord),
                                                       col - currentWord.length());
                if (indexOfWord == col || indexOfWord == col - currentWord.length() + 1) {
                    valid = false;
                    break;
                }
            }

            // Check vertically
            String verticalStr = "";
            for (int i = 0; i < result.length; i++) verticalStr += result[i][col];
            if (verticalStr.contains(currentWord)) {
                int indexOfWord = verticalStr.indexOf(currentWord, row - currentWord.length());
                if (indexOfWord == row || indexOfWord == row - currentWord.length() + 1) {
                    valid = false;
                    break;
                }
            } else if (verticalStr.contains(reverseString(currentWord))) {
                int indexOfWord = verticalStr.indexOf(reverseString(currentWord),
                                                           row - currentWord.length());
                if (indexOfWord == row || indexOfWord == row - currentWord.length() + 1) {
                    valid  = false;
                    break;
                }
            }

            // Check up right diagonal
            int tempRow = row;
            int tempCol = col;
            int counter = 0;
            String upRight = "";
            while (tempRow >= 0 && tempCol < result[0].length) {
                if (counter == currentWord.length()) break; // Only search the length of the word
                upRight += result[tempRow][tempCol];
                tempRow--;
                tempCol++;
                counter++;
            }
            if (upRight.contains(currentWord) || upRight.contains(reverseString(currentWord))) {
                valid = false;
                break;
            }
            tempRow = row;
            tempCol = col;
            counter = 0;
            String downLeft = "";
            while (tempRow < result.length && tempCol >=  0) {
                if (counter == currentWord.length()) break; // Only search the length of the word
                downLeft += result[tempRow][tempCol];
                tempRow++;
                tempCol--;
                counter++;
            }
            if (downLeft.contains(currentWord) || downLeft.contains(reverseString(currentWord))) {
                valid = false;
                break;
            }

            // Check up left diagonal
            tempRow = row;
            tempCol = col;
            counter = 0;
            String upLeft = "";
            while (tempRow >= 0 && tempCol >= 0) {
                if (counter == currentWord.length()) break; // Only search the length of the word
                upLeft += result[tempRow][tempCol];
                tempRow--;
                tempCol--;
                counter++;
            }
            if (upLeft.contains(currentWord) || upLeft.contains(reverseString(currentWord))) {
                valid = false;
                break;
            }
            tempRow = row;
            tempCol = col;
            counter = 0;
            String downRight = "";
            while (tempRow < result.length && tempCol < result[0].length) {
                if (counter == currentWord.length()) break; // Only search the length of the word
                downRight += result[tempRow][tempCol];
                tempRow++;
                tempCol++;
                counter++;
            }
            if (downRight.contains(currentWord) || downRight.contains(reverseString(currentWord))) {
                valid = false;
                break;
            }

        }
        if (!valid) result = fillInLetter(row, col);
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

    public static String getRightDiagonal(char[][] charGrid, int row, int col) {
        String ret = "";
        int hsize = charGrid.length;
        int vsize = charGrid[0].length;

        // Move row and col variables to the bottom left
        while (row < hsize && col >= 0) {
            row++;
            col--;
        }

        // Ascend up right in the grid until reaching the end
        while (row >= 0 && col < vsize) {
            ret += charGrid[row][col];
            row--;
            col++;
        }
        return ret;
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
        String ret = "   ";
        for (int col_counter = 1; col_counter <= grid[0].length; col_counter++) {
            if (col_counter / 10 > 0) {
                ret += col_counter + " ";
            } else {
                ret += col_counter + "  ";
            }
        }
        ret += "\n";
        int row_counter = 1;
        for (int i = 0; i < grid[0].length; i++) {
            if (row_counter / 10 > 0) {
                ret += row_counter + " ";
            } else {
                ret += row_counter + "  ";
            }
            for (int j = 0; j < grid.length; j++) {
                ret += grid[i][j] + "  ";
            }
            ret += "\n";
            row_counter++;
        }
        for (String word : inputs) {
            if (!unplacedWords.contains(word)) {
                ret += word + "  ";
            }
        }
        ret += "\n";
        ret += "Unplaced words: ";
        for (String word : unplacedWords) {
            ret += word + " ";
        }
        return ret;
    }
}
