package Controller;
import Model.Minesweeper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Class to reset model when the reset button is pressed
 */
public class Reset implements EventHandler<ActionEvent> {
    private Minesweeper minesweeper;

    public Reset(Minesweeper minesweeper) {
        this.minesweeper = minesweeper;
    }

    @Override
    public void handle(ActionEvent arg0) {
        // reset minesweeper fields
       minesweeper.initialize();
    }
    
}
