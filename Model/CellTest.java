package Model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;

@Testable
public class CellTest {
    @Test
    public void testConstructor() {
        // setup
        int row = 1;
        int col = 2;
        // invoke
        Cell cell = new Cell(row, col);
        // analyze
        assertNotNull(cell);
        assertEquals(row, cell.getRow());
        assertEquals(col, cell.getCol());
    }

    @Test
    public void testEqualsDiffObject() {
        // setup 
        int row = 1;
        int col = 2;
        Cell cell = new Cell(row, col);
        Object other = new Object();
        boolean expected = false;
        // invoke
        boolean actual = cell.equals(other);
        // analyze
        assertEquals(expected, actual);
    }


    @Test
    public void testEqualsDiffRowAndCol() {
        // setup 
        Cell cell = new Cell(1, 2);
        Cell otherCell = new Cell(3, 4);
        boolean expected = false;
        // invoke
        boolean actual = cell.equals(otherCell);
        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsDiffRowSameCol() {
        // setup 
        Cell cell = new Cell(1, 2);
        Cell otherCell = new Cell(3, 2);
        boolean expected = false;
        // invoke
        boolean actual = cell.equals(otherCell);
        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsSameRowDiffCol() {
        // setup 
        Cell cell = new Cell(1, 2);
        Cell otherCell = new Cell(1, 3);
        boolean expected = false;
        // invoke
        boolean actual = cell.equals(otherCell);
        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsSameRowAndCol() {
        // setup 
        Cell cell = new Cell(1, 2);
        Cell otherCell = new Cell(1, 2);
        boolean expected = true;
        // invoke
        boolean actual = cell.equals(otherCell);
        // analyze
        assertEquals(expected, actual);
    }

    @Test
    public void testHashCodeSame() {
        // setup 
        Cell cell = new Cell(1, 2);
        Cell otherCell = new Cell(1, 2);
        // invoke
        int cellHash = cell.hashCode();
        int otherCellHash = otherCell.hashCode();
        // analyze
        assertEquals(cellHash, otherCellHash);
    }

    @Test
    public void testHashCodeDiff() {
        // setup 
        Cell cell = new Cell(1, 2);
        Cell otherCell = new Cell(3, 4);
        // invoke
        int cellHash = cell.hashCode();
        int otherCellHash = otherCell.hashCode();
        // analyze
        assertNotEquals(cellHash, otherCellHash);
    }

    @Test
    public void testToString() {
        // setup 
        Cell cell = new Cell(1, 2);
        String expected = "(1, 2)";
        // invoke
        String actual = cell.toString();
        // analyze
        assertEquals(expected, actual);
    }
}


