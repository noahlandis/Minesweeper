package View;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;
import javafx.event.ActionEvent;
public class StartEvent extends TimerTask implements EventHandler<ActionEvent> {
    private int seconds = 0;
    private int minutes = 0;
    private Label lblTimer;

    public StartEvent(Label lblTimer) {
        this.lblTimer = lblTimer;
    }

    public int getSeconds() {
        return seconds;
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
        Timer timer = new Timer();
        timer.schedule(new StartEvent(lblTimer), 0, 1000);
    }    
}



