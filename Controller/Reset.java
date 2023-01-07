package Controller;

import Model.Minesweeper;
import View.MinesweeperGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class Reset implements EventHandler<ActionEvent> {
    private Minesweeper minesweeper;
    private MinesweeperGUI gui;

    public Reset(Minesweeper minesweeper, MinesweeperGUI gui) {
        this.minesweeper = minesweeper;
        this.gui = gui;
    }

    @Override
    public void handle(ActionEvent arg0) {
    //     System.out.println(minesweeper);
    //    minesweeper.initialize();
       System.out.println(minesweeper);
       
        
    }
    
}
