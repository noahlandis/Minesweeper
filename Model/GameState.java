package Model;

/**
 * Represents the possible gamestates in Minesweeper
 */
public enum GameState { 
    NOT_STARTED("Not Started"), 
    IN_PROGRESS("In Progress"), 
    WON("Congratulations!"), 
    LOST("BOOM! Better luck next time");

    private String gameState;
    private GameState(String gameState) {
        this.gameState = gameState;
    }

    /**
     * @return String representation of game state
     */
    @Override
    public String toString() {
        return gameState;
    }
}