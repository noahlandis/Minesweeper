package Model;

/**
 * Represents a cell in minesweeper
 */
public class Cell {
    private int row;
    private int col;

    /**
     * Creates a cell object
     * @param row row of cell
     * @param col col of cell
     */
    public Cell(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns cell's row
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns cell's col
     * @return col
     */
    public int getCol() {
        return col;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", row, col);
    }

    /**
     * Checks if two locations are equal based on their row on col fields
     * @return true if row and cols are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Cell) {
            Cell otherLocation = (Cell)obj;
            return row == otherLocation.row && col == otherLocation.col;
        }
        return false;
    }

    /**
     * Returns a Cell object's hash code.
     * Two cell object's will have the same hash code if their col and row fields are equal
     * @return hash code based on row and col fields
     */
    @Override
    public int hashCode() {
        return (int)(Math.pow(27, row) + Math.pow(31, col));
    }
}


