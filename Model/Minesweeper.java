package Model;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Represents a Minesweeper game
 */
public class Minesweeper  {
    private int moveCount = 0;
    public static final char MINE = 'M';
    public static final char COVERED = '-';
    public static final char[] ADJACENT_MINES = {'0', '1', '2', '3', '4', '5', '6', '7', '8'};
    private GameState gameState;
    private int rows;
    private int cols;
    private int mineCount;
    private Map<Cell, Character> map;

    /**
     * Constructs a Minesweeper object with <rows> x <columns> cells and <mineCount> randomly placed mines.
     * @param rows rows
     * @param cols columns
     * @param mineCount number of mines to be placed
     */
    public Minesweeper(int rows, int cols, int mineCount) {
        this.rows = rows;
        this.cols = cols;
        this.mineCount = mineCount;
        initialize();
    }

    /**
     * Resets the game's fields
     */
    public void initialize() {
        gameState = GameState.NOT_STARTED;
        moveCount = 0;
        map = new HashMap<>();
        placeMines(mineCount);
        // creating the board
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Cell cell = new Cell(r, c);
                // only add '-' character if mine is not already occupying that cell
                if (!map.containsKey(cell)) {
                    // game starts with every cell covered and adjacent mines haven't been determined yet
                    map.put(cell, COVERED);
                }
            }
        }
    }

    /**
     * This method places a specified amount of mines onto the board.
     * Mine's have an 'X' listed as the number of adjacent mines, as this information is irrelevant.
     * @param mineCount number of mines to be placed
     */
    private void placeMines(int mineCount) {
        int placedMines = 0;
        while (placedMines < mineCount) {
            Random random = new Random();
            Cell cell = new Cell(random.nextInt(rows), random.nextInt(cols));
            if (map.get(cell) == null) {
                placedMines++;
                map.put(cell, MINE);
            }
        }
    }

    /**
     * This is currently a test function to display the board's internal data.
     */
    private void displayInternalData() {
        for (int i = 0; i < rows; i++) {
            System.out.println();
            for (int j = 0; j < cols; j++) {
                System.out.print(new Cell(i, j) + ": " + map.get(new Cell(i, j)) + "\t\t");
            }
        }
        System.out.println();
    }

    /**
     * Displays the current game to the user, mines appear as covered. 
     * If the game has been lost or won, the true state of the game is displayed.
     * @return User's view of game
     */
    @Override
    public String toString() {
        String output = "";
        for (int i = 0; i < rows; i++) {
            output += "\n";
            for (int j = 0; j < cols; j++) {
                Cell cell = new Cell(i, j);
                // hide mine cells from user by displaying them as covered
                if (gameState == GameState.LOST || gameState == GameState.WON) {
                    if (map.get(cell) == COVERED) {
                        output += getAdjacentMines(cell);
                    }
                    else {
                        output += map.get(cell);
                    }
                }
                else if (map.get(cell) == MINE) {
                    output += COVERED;
                    }
                else {
                    output += map.get(new Cell(i, j));
                }
            }
        }
        return output;
    }

    /**
     * Attempt to make a move
     * @param cell location at which attempted move is made
     * @throws MinesweeperException
     */
    public void makeSelection(Cell cell) throws MinesweeperException  {
        if (map.get(cell) == null || (map.get(cell) != COVERED && map.get(cell) != MINE)) {
            throw new MinesweeperException();
        }
        if (map.get(cell) == MINE) {
            gameState = GameState.LOST;
        }
        else {
            gameState = GameState.IN_PROGRESS;
            map.put(cell, getAdjacentMines(cell));
        }
        if (!map.containsValue(COVERED)) {
            gameState = GameState.WON;
        }
        moveCount++;
    }

    private char getAdjacentMines(Cell cell) {
        // array of possible mine cells based on passed cell's position.
        Cell[] possibleAdjacentMinecells = {
            new Cell(cell.getRow(), cell.getCol()+1), 
            new Cell(cell.getRow(), cell.getCol()-1),
            new Cell(cell.getRow()+1, cell.getCol()),
            new Cell(cell.getRow()-1, cell.getCol()),
            new Cell(cell.getRow()+1, cell.getCol()+1),
            new Cell(cell.getRow()+1, cell.getCol()-1),
            new Cell(cell.getRow()-1, cell.getCol()+1),
            new Cell(cell.getRow()-1, cell.getCol()-1)
        };
        char adjacentMines = '0';
        for (Cell surroundingcell: possibleAdjacentMinecells) {
            // first ensure that surrounding cell is not out of board's bounds, then check if there is a mine at cell
            if (map.containsKey(surroundingcell) && map.get(surroundingcell) == MINE) {
                adjacentMines++;
            }
        }
        return adjacentMines;
    }

    /**
     * Move count accessor.
     * @return moveCount
     */
    public int getMoveCount() {
        return moveCount;
    }

    /**
     * Game state accessor.
     * @return The current state of the game.
     */
    public GameState getGameState() {
        return gameState;
    }


    /**
     * Returns a symbol (represented as a char) at a given cell
     * @param cell cell object consisting of a row and col
     * @return symbol at specified cell
     */
    public char getSymbol(Cell cell) {
        return map.get(cell);
    }

    /**
     * Returns a boolean value indicating if given position is covered
     * @param cell a given cell consisting of a row and col
     * @return true if that cell is covered (represented with '-'), false otherwise
     */
    public boolean isCovered(Cell cell) {
        return map.get(cell) == COVERED;
    }

    /**
     * This method generates a list of all valid cells a player may choose from.
     * @return list of possible selections on the board with its current configuration.
     */
    public Collection<Cell> getPossibleSelections() {
        // create an empty list
        List<Cell> list = new LinkedList<>();
        // if the cell is covered, add it to the list
        for(Cell cell : map.keySet()) {
            if(map.get(cell) == COVERED) {
                list.add(cell);
            }
        }
        return list;
    }
}