package Controller;

import Model.Cell;
import Model.Minesweeper;
import Model.MinesweeperException;
import View.MinesweeperGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Class to handle uncovering a cell
 */
public class UncoverCell implements EventHandler<ActionEvent> {
    private Minesweeper minesweeper;
    private MinesweeperGUI gui;
    private int row;
    private int col;
    
    
    public UncoverCell(int row, int col, Minesweeper minesweeper, MinesweeperGUI gui) {
        this.row = row;
        this.col = col;
        this.minesweeper = minesweeper;
        this.gui = gui;
    }
    
    @Override
    public void handle(ActionEvent arg0) {
        try {
            // try to update model with uncovered cell
            minesweeper.makeSelection(new Cell(row, col));
        } 
        catch (MinesweeperException e) {
            // Update status bar to reflect invalid move if cell has already been uncovered
            gui.getStatusBar().setText(minesweeper.getGameState() + ": " + e.getMessage());
        }
    }
}
