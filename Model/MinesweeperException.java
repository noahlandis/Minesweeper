package Model;

/**
 * An exception indicating the player attempted to make an invalid move.
 */
public class MinesweeperException extends Exception {

    public MinesweeperException() {
        super("Invalid move.");
    }
}