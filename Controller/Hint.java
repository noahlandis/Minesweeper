package Controller;

import java.io.File;
import Model.Cell;
import Model.Minesweeper;
import View.MinesweeperGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class to display hint when 'hint' button is pressed
 */
public class Hint implements EventHandler<ActionEvent>  {
    private Minesweeper minesweeper;
    private MinesweeperGUI gui;

    public Hint(Minesweeper minesweeper, MinesweeperGUI gui) {
        this.minesweeper = minesweeper;
        this.gui = gui;
    }

    @Override
    public void handle(ActionEvent arg0) {
        // get a safe (non-mine-occupied) cell from the model
        Cell hintCell = (Cell)minesweeper.getPossibleSelections().toArray()[0];
        Button btn = gui.getButtonAtLocation(hintCell.getRow(), hintCell.getCol());
        btn.getStylesheets().clear();
        // highlight button corresponding to hintCell
        btn.getStylesheets().add("View/Assets/CSS/cell_button_hint.css");
        Media sound = new Media(new File("View/Assets/Audio/ms_hint.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    
}
