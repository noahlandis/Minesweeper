package Controller;

import Model.Cell;
import Model.Minesweeper;
import Model.MinesweeperException;
import View.MinesweeperGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Controller implements EventHandler<ActionEvent> {
    private Minesweeper minesweeper;
    private MinesweeperGUI gui;
    private int row;
    private int col;
    

    public Controller(int row, int col, Minesweeper minesweeper, MinesweeperGUI gui) {
        this.row = row;
        this.col = col;
        this.minesweeper = minesweeper;
        this.gui = gui;
    }
    
    @Override
    public void handle(ActionEvent arg0) {
        try {
            minesweeper.makeSelection(new Cell(row, col));
            System.out.println(minesweeper);
            
            gui.getStatusBar().setText(minesweeper.getGameState().toString());

            
        } catch (MinesweeperException e) {
            gui.getStatusBar().setText(minesweeper.getGameState() + ": " + e.getMessage());
        }
    }
}