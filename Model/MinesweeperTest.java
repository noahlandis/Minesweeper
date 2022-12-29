package Model;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class MinesweeperTest {
    private Minesweeper minesweeper;
    @Test
    public void testMinesweeperInitial() {
        // setup
        minesweeper = new Minesweeper(2, 2, 0);
        // analyze
        assertEquals(0, minesweeper.getMoveCount());        
        assertEquals(GameState.NOT_STARTED, minesweeper.getGameState());   
        assertEquals(4,  minesweeper.getPossibleSelections().size());  
    }

    @Test
    public void testMinesweeperMakeSelectionValid() throws MinesweeperException {
        // setup
        minesweeper = new Minesweeper(2, 2, 0);
        // invoke
        minesweeper.makeSelection(new Cell(0, 0));
        // analyze
        assertEquals(1, minesweeper.getMoveCount());
        assertEquals(GameState.IN_PROGRESS, minesweeper.getGameState());          
    }

    @Test
    public void testMinesweeperMakeSelectionInvalid() {
        // setup
        minesweeper = new Minesweeper(1, 1, 0);
        // invoke/analyze
        assertThrows(MinesweeperException.class, () -> minesweeper.makeSelection(new Cell(10, 10)));
        assertEquals(GameState.NOT_STARTED,  minesweeper.getGameState());
        assertEquals(minesweeper.getMoveCount(), 0);     
    }

    @Test
    public void testMinesweeperMakeSelectionMine() throws MinesweeperException {
        // setup
        minesweeper = new Minesweeper(10, 10, 99);
        // invoke
        minesweeper.makeSelection(new Cell(0, 0));
        // analyze
        assertEquals(GameState.LOST,  minesweeper.getGameState());
        assertEquals(minesweeper.getMoveCount(), 1);        
    }

    @Test
    public void testMinesweeperWon() throws MinesweeperException {
        minesweeper = new Minesweeper(2, 2, 0);
        minesweeper.makeSelection(new Cell(0, 0));
        minesweeper.makeSelection(new Cell(0, 1));
        minesweeper.makeSelection(new Cell(1, 0));
        minesweeper.makeSelection(new Cell(1, 1));
        assertEquals(GameState.WON,  minesweeper.getGameState());
    }

    @Test
    public void testGetPossibleSelections() throws MinesweeperException {
        minesweeper = new Minesweeper(2, 2, 0);
        minesweeper.makeSelection(new Cell(0, 0));
        assertEquals(3,  minesweeper.getPossibleSelections().size());
    }

    @Test
    public void testIsCoveredTrue() {
        // setup
        minesweeper = new Minesweeper(2, 2, 0);
        boolean expected = true;
        // invoke
        boolean actual = minesweeper.isCovered(new Cell(0, 0));
        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testIsCoveredFalse() throws MinesweeperException {
        // setup
        minesweeper = new Minesweeper(2, 2, 0);
        Cell moveMade = new Cell(0, 0);
        minesweeper.makeSelection(moveMade);
        boolean expected = false;
        // invoke
        boolean actual = minesweeper.isCovered(moveMade);
        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSymbol() {
        // setup
        minesweeper = new Minesweeper(2, 2, 0);
        Cell Cell = new Cell(0, 0);
        char expected = Minesweeper.COVERED;
        // invoke
        char actual = minesweeper.getSymbol(Cell);
        // analyze
        assertEquals(expected, actual);
    }
}
