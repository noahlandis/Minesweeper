package View;

import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;

import Model.GameState;
import Model.Minesweeper;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MinesweeperGUI extends Application {
    private final static int ROWS = 4;
    private final static int COLS = 4;
    private final static int MINE_COUNT = 2;
    private Minesweeper minesweeper = new Minesweeper(ROWS, COLS, MINE_COUNT);
    private ImageView hintIcon = new ImageView(new Image("View/Assets/Images/Hint.png"));
    private final ImageView RESET_ICON = new ImageView(new Image("View/Assets/Images/Reset.png"));
    private final ImageView START_ICON = new ImageView(new Image("View/Assets/Images/Start.png"));

    private final Font FONT = new Font("MS Reference Sans Serif", 30);


    /**
     * GUI setup
     */
    @Override
    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();
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
        Label lblMoves = makeLabel("Moves: " + minesweeper.getMoveCount());
        Button btnHint = makeSideBarButton(hintIcon);
        Button btnReset = makeSideBarButton(RESET_ICON);
        Button btnStart = makeSideBarButton(START_ICON);
        Label lblTimer = makeLabel("0:00");
        lblTimer.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        lblTimer.setAlignment(Pos.CENTER);
        lblTimer.setTextAlignment(TextAlignment.CENTER);
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
        Label lblStatus = new Label();
        lblStatus.setMaxWidth(Double.MAX_VALUE);
        lblStatus.setBackground(new Background(new BackgroundFill(Color.ORANGE, null, null)));
        lblStatus.setText(GameState.NOT_STARTED.toString());
        lblStatus.setAlignment(Pos.CENTER);
        return lblStatus;
    }

    private GridPane grid() {
        GridPane grid = new GridPane();
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
        btn.getStylesheets().add("View/cell_button.css");   
        return btn;
    }

    private Button makeSideBarButton(ImageView image) {
        Button btn = new Button();
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
        btn.getStylesheets().add("View/sidebar_button.css");
        return btn;
    }

    private Label makeLabel(String text) {
        Label lbl = new Label(text);
        lbl.setPadding(new Insets(10));
        lbl.setFont(FONT);
        lbl.setTextFill(Color.GREEN);
        return lbl;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
