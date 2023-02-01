import java.util.ArrayList;

/**
 *  WORD SEARCH PROJECT
 *  Beginning date: Oct. 27th, 2022
 *  End date:
 */
public class WordSearcher {
    private char[][] grid;
    private ArrayList<String> notFound;
    private ArrayList<String> found;

    public WordSearcher(Gridmaker grid) {
        this.grid = grid.getGrid();
        this.notFound = grid.getInputs();
        this.found = new ArrayList<>();
    }

    /**
     * Searches for all of the words in the given grid
     *
     * @Return a formatted string with all of the words and their starting index.
     */
    public String searchWords() {
        String ret = "";
        ArrayList<String> notFoundCopy = new ArrayList<>();
        for (String s : notFound) {
            notFoundCopy.add(s);
        }

        // Search rows
        int row_counter = 0;
        for (char[] row : grid) {
            String rowStr = String.valueOf(row);
            for (String word : notFoundCopy) {
                if (rowStr.contains(word)) {
                    int indexOfWord = rowStr.indexOf(word);
                    ret += addFoundStatement(word, row_counter, indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                } else if (rowStr.contains(Gridmaker.reverseString(word))) {
                    int indexOfWord = rowStr.indexOf(Gridmaker.reverseString(word)) + word.length() - 1;
                    ret += addFoundStatement(word, row_counter, indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                }
            }
            row_counter++;
        }

        // Search columns
        for (int col = 0; col < grid[0].length; col++) {
            String colStr = "";
            for (int i = 0; i < grid.length; i++) {
                colStr += grid[i][col];
            }
            for (String word : notFoundCopy) {
                if (colStr.contains(word)) {
                    int indexOfWord = colStr.indexOf(word);
                    ret += addFoundStatement(word, indexOfWord, col);
                    notFound.remove(word);
                    found.add(word);
                } else if (colStr.contains(Gridmaker.reverseString(word))) {
                    int indexOfWord = colStr.indexOf(Gridmaker.reverseString(word)) + word.length();
                    ret += addFoundStatement(word, indexOfWord, col);
                    notFound.remove(word);
                    found.add(word);
                }
            }
        }

        // Search up right diagonal
        // Looping through each coordinate on the first row by varying the column
        int c = 0;
        while (c < grid[0].length) {
            String rightDiag = getRightDiagonal(grid, 0, c);
            for (String word : notFoundCopy) {
                if (rightDiag.contains(word)) {
                    int indexOfWord = rightDiag.indexOf(word);
                    ret += addFoundStatement(word, c - indexOfWord, indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                } else if (rightDiag.contains(Gridmaker.reverseString(word))) {
                    int indexOfWord = rightDiag.indexOf(Gridmaker.reverseString(word)) + word.length() - 1;
                    ret += addFoundStatement(word, c - indexOfWord, indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                }
            }
            c++;
        }
        // Looping through each coordinate on the first column by varying the row
        int r = 1;
        while (r < grid.length) {
            String rightDiag = getRightDiagonal(grid, r, grid[0].length);
            for (String word : notFoundCopy) {
                if (rightDiag.contains(word)) {
                    int indexOfWord = rightDiag.indexOf(word);
                    ret += addFoundStatement(word, grid.length - indexOfWord - 1, r + indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                } else if (rightDiag.contains(Gridmaker.reverseString(word))) {
                    int indexOfWord = rightDiag.indexOf(Gridmaker.reverseString(word)) + word.length() - 1;
                    ret += addFoundStatement(word, grid.length - indexOfWord - 1, r + indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                }
            }
            r++;
        }

        // Search up left diagonal
        // Looping through each coordinate on the first column by varying the row
        r = 0;
        while (r < grid[0].length) {
            String leftDiag = getLeftDiag(grid, r, 0);
            for (String word : notFoundCopy) {
                if (leftDiag.contains(word)) {
                    int indexOfWord = leftDiag.indexOf(word);
                    ret += addFoundStatement(word, r + indexOfWord, indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                } else if (leftDiag.contains(Gridmaker.reverseString(word))) {
                    int indexOfWord = leftDiag.indexOf(Gridmaker.reverseString(word)) + word.length() - 1;
                    ret += addFoundStatement(word, r + indexOfWord, indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                }
            }
            r++;
        }
        // Looping through each coordinate on the first row by varying the column
        c = 1;
        while (c < grid[0].length) {
            String leftDiag = getLeftDiag(grid, 0, c);
            for (String word : notFoundCopy) {
                if (leftDiag.contains(word)) {
                    int indexOfWord = leftDiag.indexOf(word);
                    ret += addFoundStatement(word, indexOfWord, c + indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                } else if (leftDiag.contains(Gridmaker.reverseString(word))) {
                    int indexOfWord = leftDiag.indexOf(Gridmaker.reverseString(word)) + word.length() - 1;
                    ret += addFoundStatement(word,  indexOfWord, c + indexOfWord);
                    notFound.remove(word);
                    found.add(word);
                }
            }
            c++;
        }
        return ret;
    }

    /**
     * Gets the diagonal of a 2D char grid spanning from the botton left
     * to upper right.
     * @Param a 2D char grid, a specified row and a specified column
     * @Return a string starting from the bottom left to the upper right of the 2D array
     */
    public String getRightDiagonal(char[][] grid, int row, int col) {
        String ret = "";
        while (row < grid.length - 1 && col > 0) {
            row++;
            col--;
        }
        while (row >= 0 && col < grid[0].length) {
            ret += grid[row][col];
            row--;
            col++;
        }
        return ret;
    }

    /**
     * Gets the diagonal of a 2D char grid spanning from the top left
     * to bottom right.
     * @Param a 2D char grid, a specified row and a specified column
     * @Return a string starting from the top left to the bottom right of the 2D array
     */
    public String getLeftDiag(char[][] grid, int row, int col) {
        String ret = "";
        while (row > 0 && col > 0) {
            row--;
            col--;
        }
        while (row < grid.length && col < grid[0].length) {
            ret += grid[row][col];
            row++;
            col++;
        }
        return ret;
    }

    public String addFoundStatement(String word, int row, int col) {
        return String.format("%s can be found at row %d and column %d\n", word, row, col);
    }

}
