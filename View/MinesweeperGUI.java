package View;

import java.beans.EventHandler;
import java.io.File;
import java.security.Key;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.Timer;

import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import javafx.event.ActionEvent;
import javafx.event.Event;
import Controller.Controller;
import Controller.Hint;
import Controller.Reset;
import Model.Cell;
import Model.GameState;
import Model.Minesweeper;
import Model.MinesweeperObserver;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
// import javafx.event.EventHandler;

public class MinesweeperGUI extends Application  {
    private final static int ROWS = 10;
    private final static int COLS = 10;
    private final static int MINE_COUNT = 10;
    private Minesweeper minesweeper = new Minesweeper(ROWS, COLS, MINE_COUNT);
    private ImageView hintIcon = new ImageView(new Image("View/Assets/Images/Hint.png"));
    private final ImageView RESET_ICON = new ImageView(new Image("View/Assets/Images/Reset.png"));
    private final ImageView START_ICON = new ImageView(new Image("View/Assets/Images/Start.png"));
    private final ImageView MINE_ICON = new ImageView(new Image("View/Assets/Images/Mine.png"));
    private final String FONT_STRING = "MS Reference Sans Serif";
    private Label lblTimer;
    private Button btnStart;
    private Button btnReset;
    private Label lblStatus;
    private Label lblMoves;
    private int r;
    private int c;
    private GridPane grid;
    private Button btn;
    private Media sound;
    private MediaPlayer mediaPlayer;
    private String audioPath;
    private Timeline timeline;
    private static int i = 1;
    private Stage stage;
    BorderPane root;
    private StartEvent startEvent;
    /**
     * GUI setup
     */
    @Override
    public void start(Stage stage) throws Exception {

        root = new BorderPane();
        timeline = new Timeline();
        Scene scene = new Scene(root, Double.MAX_VALUE, Double.MAX_VALUE);
        root.setCenter(grid());
        root.setLeft(sideBar());
        root.setBottom(statusBar());
        stage.setScene(scene);
        stage.setTitle("Minesweeper");
        stage.setMaximized(true);
        stage.show();
    }

    

    /**
     * @return GUI's sidebar comprising of labels for mineCount and moveCount, buttons for hint and reset
     */
    private VBox sideBar() {
        VBox sideBar = new VBox();
        Label lblMines = makeLabel("Mines: " + MINE_COUNT);
        lblMoves = makeLabel("Moves: " + minesweeper.getMoveCount());
        Button btnHint = makeSideBarButton(hintIcon);
        btnHint.setOnAction(new Hint(minesweeper, this));

        btnReset = makeSideBarButton(RESET_ICON);
        btnReset.setOnAction(new Reset(minesweeper, this));
        btnStart = makeSideBarButton(START_ICON);
        lblTimer = makeLabel("00:00");
        lblTimer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lblTimer.setAlignment(Pos.CENTER);
        lblTimer.setTextAlignment(TextAlignment.CENTER);
        startEvent = new StartEvent(lblTimer, this, minesweeper);
        btnStart.setOnAction(startEvent);

        VBox.setVgrow(lblTimer, Priority.ALWAYS);
        sideBar.getChildren().addAll(
            lblMines,
            lblMoves,
            btnStart,
            btnHint,
            btnReset,
            lblTimer
        );
        sideBar.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), Insets.EMPTY)));
        return sideBar;
    }

    private Label statusBar() {
        lblStatus = new Label();
        lblStatus.setMaxWidth(Double.MAX_VALUE);
        lblStatus.setFont(new Font(FONT_STRING, 15));
        lblStatus.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
        lblStatus.setText(GameState.NOT_STARTED.toString());
        lblStatus.setAlignment(Pos.CENTER);
        return lblStatus;
    }

   

    private GridPane grid() {
        grid = new GridPane();
        for (int col = 0; col < COLS; col++) {
            for (int row = 0; row < ROWS; row++) {
                Button btn = makeButton(row, col);
                GridPane.setHgrow(btn, Priority.ALWAYS);
                GridPane.setVgrow(btn, Priority.ALWAYS);
                grid.add(btn, col, row);                
            }
        }
        return grid;
    }

    private Button makeButton(int row, int col) {
        Button btn = new Button();
        btn.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        btn.setMinSize(btn.getWidth(), btn.getHeight());
        btn.setPrefSize(btn.getWidth(), btn.getHeight());
        btn.setOnAction(new Controller(row, col, minesweeper, this));
        btn.getStylesheets().add("View/Assets/CSS/cell_button.css");
        btn.setDisable(true);
        minesweeper.register(new MinesweeperObserver() {
            @Override
            public void cellClicked(Cell cell) {
                lblStatus.setText(minesweeper.getGameState().toString());
                lblMoves.setText("Moves: " + minesweeper.getMoveCount());
                Color color = null;
                if (minesweeper.getGameState() == GameState.NOT_STARTED) {
                    color = Color.ORANGE;
                    btn.getStylesheets().clear();
                    btn.setText("");
                    btn.getStylesheets().add("View/Assets/CSS/cell_button.css");
                    if (btn.getGraphic() != null) {
                        btn.getGraphic().setVisible(false);
                    }
                    timeline.pause();
                    startEvent.reset();
                    btn.setDisable(true);
                }
            
                else if (minesweeper.getGameState() == GameState.IN_PROGRESS) {
                    color = Color.YELLOW;
                    btn.setDisable(false);
                    
                }
                else if (minesweeper.getGameState() == GameState.WON) {
                    color = Color.GREEN;
                }
                else if (minesweeper.getGameState() == GameState.LOST) {
                    color = Color.RED;
                }
                lblStatus.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
                if (cell == null) {
                    return;
                }
                if (row == cell.getRow() && col == cell.getCol() && minesweeper.getGameState() != GameState.NOT_STARTED) {
                    updateButton(btn);
                    if (mediaPlayer != null) {
                        mediaPlayer.play();
                    }
                }
                if (minesweeper.getGameState() == GameState.WON || minesweeper.getGameState() == GameState.LOST) {
                    revealBoard(btn);
                    if (audioPath != null && audioPath.equals("View/Assets/Audio/msExplode.mp3")) {
                        mediaPlayer.play();
                    }
                }
            }
        }); 
        return btn;
    }




    public int getR() {
        System.out.println(r);
        return r;
    }

    public int getC() {
        System.out.println(c);
        return c;
    }

    

    public Button getButtonAtLocation(int row, int col) {
        Node result = null;
        for (Node node: grid.getChildren()) {
            
            if (grid.getRowIndex(node) == row && grid.getColumnIndex(node) == col) {
                result = node;
                break;
            }
        }

        return (Button)result;
    }

    private Button makeSideBarButton(ImageView image) {
        btn = new Button();
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.maxHeightProperty().bind(btn.widthProperty());
        VBox.setVgrow(btn, Priority.ALWAYS);
        image.setFitHeight(100);
        image.setFitWidth(100);
        Lighting lighting = new Lighting(new Light.Distant(45, 90, Color.GREEN));
        ColorAdjust bright = new ColorAdjust(0, 1, 1, 1);
        lighting.setContentInput(bright);
        image.setEffect(lighting);
        btn.setGraphic(image);
        btn.getStylesheets().add("View/Assets/CSS/sidebar_button.css");
        return btn;
    }

    private Label makeLabel(String text) {
        Label lbl = new Label(text);
        lbl.setPadding(new Insets(10));
        lbl.setFont(new Font(FONT_STRING, 30));
        lbl.setTextFill(Color.GREEN);
        return lbl;
    }

    public Button getBtnStart() {
        return btnStart;
    }

    public Label getLblTimer() {
        return lblTimer;
    }

    public Label getStatusBar() {
        return lblStatus;
    }
    
    /**
     * @param btn
     */
    public void revealBoard(Button btn) {
        
        btn.setDisable(true);
            timeline.getKeyFrames().add(new KeyFrame(javafx.util.Duration.millis(i+= 120), e -> {
                updateButton(btn);
            }));
        timeline.playFromStart();
    }

    public void enableAllButtons() {
        // for (Node node: grid.getChildren()) {
        //     Button btn = (Button)node;
        //     btn.setDisable(false);
        // }        
    }
            

    

    

    public static void main(String[] args) {
        launch(args);
    }



    public void updateButton(Button btn) {
        char symbol = minesweeper.getHiddenSymbol(new Cell(GridPane.getRowIndex(btn), GridPane.getColumnIndex(btn)));
        Color color = null;
        switch (symbol) { 
            case '1':
                color = Color.BLUE;
                audioPath = "View/Assets/Audio/ms1Clippedn.mp3";    
                break;
            case '2':
                color = Color.GREEN;
                audioPath = "View/Assets/Audio/ms2Clipped.mp3";    
                break;
            case '3':
                color = Color.RED;
                audioPath = "View/Assets/Audio/ms3Clipped.mp3";    
                break;
            case '4':
                color = Color.PURPLE;
                audioPath = "View/Assets/Audio/ms4Clipped.mp3";    
                break;
            case '5':
                color = Color.MAROON;
                audioPath = "View/Assets/Audio/ms5Clipped.mp3";    
                break;
            case '6':
                color = Color.TURQUOISE;
                audioPath = "View/Assets/Audio/ms6Clipped.mp3";    
                break;
            case '7':
                color = Color.BLACK;
                audioPath = "View/Assets/Audio/ms7Clipped.mp3";    
                break;
            case '8':
                color = Color.GRAY;
                audioPath = "View/Assets/Audio/ms8Clipped.mp3";    
                break;
            case 'M':
                MINE_ICON.setFitHeight(btn.getWidth() / 3);
                MINE_ICON.setFitWidth(btn.getWidth() / 3);
                ImageView image = new ImageView(new Image("View/Assets/Images/Mine.png"));
                image.setFitHeight(btn.getWidth() / 3);
                image.setFitWidth(btn.getWidth() / 3);
                btn.setGraphic(image);
                audioPath = "View/Assets/Audio/msExplode.mp3";    
                break;
        }
        if (audioPath != null) {
            Media sound = new Media(new File(audioPath).toURI().toString());
            mediaPlayer = new MediaPlayer(sound);
        }
    
        btn.setText(String.valueOf(symbol));
        Font font = new Font(FONT_STRING, btn.getWidth() / 3);
        btn.setFont(font);
        btn.setTextFill(color);
        btn.setTextAlignment(TextAlignment.CENTER);
        btn.getStylesheets().clear();
        btn.getStylesheets().add("View/Assets/CSS/cell_button_disabled.css");
    }

    
}
