package View;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import java.util.Timer;
import java.util.TimerTask;

import Model.GameState;
import Model.Minesweeper;
import javafx.application.Platform;
import javafx.event.ActionEvent;
public class StartEvent extends TimerTask implements EventHandler<ActionEvent> {
    private int seconds = 0;
    private int minutes = 0;
    private Label lblTimer;
    private Timer timer;
    private MinesweeperGUI gui;
    private Minesweeper minesweeper;
    public StartEvent(Label lblTimer, MinesweeperGUI gui, Minesweeper minesweeper) {
        this.lblTimer = lblTimer;
        this.gui = gui;
        this.minesweeper = minesweeper;
    }

    public int getSeconds() {
        return seconds;
    }

    public void reset() {
        lblTimer.setText("00:00");
        timer.cancel();
        

    }

    public int getMinutes() {
        return minutes;
    }

    @Override
    public void run() {
        Platform.runLater(() -> {
            lblTimer.setText(String.format("%02d:%02d", this.getMinutes(), this.getSeconds()));
        });
            if(seconds < 59) {
                seconds++;
            }
            else {
                minutes++;
                seconds = 0;
            }
        }
    
    @Override
    public void handle(ActionEvent arg0) {
        timer = new Timer();
        timer.schedule(new StartEvent(lblTimer, gui, minesweeper), 0, 1000);
        minesweeper.setGameState(GameState.IN_PROGRESS);
        
    }    
}



