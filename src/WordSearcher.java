import java.util.ArrayList;

/**
 *  WORD SEARCH PROJECT
 *  Beginning date: Oct. 27th, 2022
 *  End date:
 */
public class WordSearcher {
    private char[][] grid;
    private ArrayList<String> inputs;

    public WordSearcher(Gridmaker grid) {
        this.grid = grid.getGrid();
        this.inputs = grid.getInputs();
    }

    public String searchWords() {
        /**
         * Searches for all of the words in the given grid
         *
         * Returns a formatted string with all of the words and their starting index.
         */
        String ret = "";
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                for (String word : inputs) {
                    if (grid[i][j] == word.charAt(0)) {
                        boolean found = searchSurrounding(i, j, word);
                        if (found) {
                            ret += String.format("%s can be found at row %d and column %d\n", word, i + 1, j + 1);
                        }
                    }
                }
            }
        }
        return ret;
    }

    public boolean searchSurrounding(int row, int col, String word) {
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
        boolean ret = false;
        // Work on the corners first
        if (row == 0 && col == grid[0].length - 1) { // Top right corner
            if (!ret) ret = searchLeft(row, col, word);
            if (!ret) ret = searchBottomLeft(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
        } else if (row == 0 && col == 0) { // Top left corner
            if (!ret) ret = searchRight(row, col, word);
            if (!ret) ret = searchBottomRight(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
        } else if (row == grid.length - 1 && col == 0) { // Bottom left corner
            if (!ret) ret = searchUp(row, col, word);
            if (!ret) ret = searchUpRight(row, col, word);
            if (!ret) ret = searchRight(row, col, word);
        } else if (row == grid.length - 1 && col == grid[0].length - 1) { // Bottom right corner
            if (!ret) ret = searchUp(row, col, word);
            if (!ret) ret = searchUpLeft(row, col, word);
            if (!ret) ret = searchLeft(row, col, word);
        } else if (row == 0 && col == grid[0].length - 1) { // Top right corner
            if (!ret) ret = searchLeft(row, col, word);
            if (!ret) ret = searchBottomLeft(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
        // Work on the edges
        } else if (row == 0) { // Top edge
            if (!ret) ret = searchRight(row, col, word);
            if (!ret) ret = searchLeft(row, col, word);
            if (!ret) ret = searchBottomLeft(row, col, word);
            if (!ret) ret = searchBottomRight(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
        } else if (row == grid.length - 1) { // Bottom edge
            if (!ret) ret = searchRight(row, col, word);
            if (!ret) ret = searchLeft(row, col, word);
            if (!ret) ret = searchUpLeft(row, col, word);
            if (!ret) ret = searchUpRight(row, col, word);
            if (!ret) ret = searchUp(row, col, word);
        } else if (col == 0) { // Left edge
            if (!ret) ret = searchRight(row, col, word);
            if (!ret) ret = searchUp(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
            if (!ret) ret = searchUpRight(row, col, word);
            if (!ret) ret = searchBottomRight(row, col, word);
        } else if (col == grid[0].length - 1) { // Right edge
            if (!ret) ret = searchLeft(row, col, word);
            if (!ret) ret = searchUp(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
            if (!ret) ret = searchBottomLeft(row, col, word);
            if (!ret) ret = searchUpLeft(row, col, word);
        // Work on the rest of the grid
        } else {
            if (!ret) ret = searchLeft(row, col, word);
            if (!ret) ret = searchRight(row, col, word);
            if (!ret) ret = searchUp(row, col, word);
            if (!ret) ret = searchDown(row, col, word);
            if (!ret) ret = searchUpRight(row, col, word);
            if (!ret) ret = searchUpLeft(row, col, word);
            if (!ret) ret = searchBottomLeft(row, col, word);
            if (!ret) ret = searchBottomRight(row, col, word);
        }

        return ret;
    }


    public boolean continuedSearch(int row, int col, String word, int orientation) {
        boolean isFound = true;
        int i;
        switch (orientation) {
            case 1: // right
                i = 0;
                while (i < word.length() && col < grid[0].length && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    i++;
                    col++;
                    if (col == grid[0].length) {
                        isFound = false;
                    }
                }
                break;
            case 2: // left
                i = 0;
                while (i < word.length() && col >= 0 && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    i++;
                    col--;
                    if (col == -1) {
                        isFound = false;
                    }
                }
                break;
            case 3: // up
                i = 0;
                while (i < word.length() && row >= 0 && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    i++;
                    row--;
                    if (row == -1) {
                        isFound = false;
                    }
                }
                break;
            case 4: // down
                i = 0;
                while (i < word.length() && row < grid.length && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    i++;
                    row++;
                    if (row == grid.length) {
                        isFound = false;
                    }
                }
                break;
            case 5: // up right
                i = 0;
                while (i < word.length() && row >= 0 && col < grid[0].length && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    row--;
                    col++;
                    i++;
                    if (row == -1 || col == grid[0].length) {
                        isFound = false;
                    }
                }
                break;
            case 6: // up left
                i = 0;
                while (i < word.length() && row >= 0 && col >= 0 && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    row--;
                    col--;
                    i++;
                    if (row == -1 || col == -1) {
                        isFound = false;
                    }
                }
                break;
            case 7: // down left
                i = 0;
                while (i < word.length() && row < grid.length && col >= 0 && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    row++;
                    col--;
                    i++;
                    if (row == grid.length || col == -1) {
                        isFound = false;
                    }
                }
                break;
            case 8: // down right
                i = 0;
                while (i < word.length() && row < grid.length && col < grid[0].length && isFound) {
                    if (grid[row][col] != word.charAt(i)) {
                        isFound = false;
                    }
                    row++;
                    col++;
                    i++;
                    if (row == grid.length || col == grid[0].length) {
                        isFound = false;
                    }
                }
                break;
        }

        return isFound;
    }

    public final boolean searchRight(int row, int col, String word) {
        boolean found = false;
        if (grid[row][col + 1] == word.charAt(1)) { // Search right
            found = continuedSearch(row, col, word, 1);
        }
        return found;
    }
    public final boolean searchLeft(int row, int col, String word) {
        boolean found = false;
        if (grid[row][col - 1] == word.charAt(1)) { // Search left
            found = continuedSearch(row, col, word, 2);
        }
        return found;
    }
    public final boolean searchUp(int row, int col, String word) {
        boolean found = false;
        if (grid[row - 1][col] == word.charAt(1)) { // Search Up
            found = continuedSearch(row, col, word, 3);
        }
        return found;
    }
    public final boolean searchDown(int row, int col, String word) {
        boolean found = false;
        if (grid[row + 1][col] == word.charAt(1)) { // Search Down
            found = continuedSearch(row, col, word, 4);
        }
        return found;
    }
    public final boolean searchUpRight(int row, int col, String word) {
        boolean found = false;
        if (grid[row - 1][col + 1] == word.charAt(1)) { // Search Up Right
            found = continuedSearch(row, col, word, 5);
        }
        return found;
    }
    public final boolean searchUpLeft(int row, int col, String word) {
        boolean found = false;
        if (grid[row - 1][col - 1] == word.charAt(1)) { // Search Up Left
            found = continuedSearch(row, col, word, 6);
        }
        return found;
    }
    public final boolean searchBottomLeft(int row, int col, String word) {
        boolean found = false;
        if (grid[row + 1][col - 1] == word.charAt(1)) { // Search Bottom Left
            found = continuedSearch(row, col, word, 7);
        }
        return found;
    }
    public final boolean searchBottomRight(int row, int col, String word) {
        boolean found = false;
        if (grid[row + 1][col + 1] == word.charAt(1)) { // Search Bottom Right
            found = continuedSearch(row, col, word, 7);
        }
        return found;
    }



}
