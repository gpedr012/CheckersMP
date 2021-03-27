package checkers.client.scenes;


import checkers.client.game.GameManager;
import checkers.client.game.HumanPlayer;
import checkers.client.game.OnlineGameManager;
import checkers.client.game.Player;
import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class GameLoop extends Application {


    Player player1;
    Player player2;
    Board board;
    Scene scene;
    Stage stage;
    Pane rootContainer;


    public GameLoop() {
        this.board = new Board();
        this.player1 = new HumanPlayer(Piece.PieceColor.DARK, 1, board);
        this.player2 = new HumanPlayer(Piece.PieceColor.LIGHT, 2, board);
    }

    public GameLoop(Board board, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;

    }

    public GameLoop(Board board, Player player1) {
        this.player1 = player1;
        this.board = board;

    }

    @Override
    public void start(Stage stage) throws Exception {
        GameManager gameManager = new GameManager(board, player1, player2, this);
        setUpStage(stage);
        gameManager.startGame();


    }


    public void startOnline(Stage mainStage) {
        OnlineGameManager.initialize(board, player1);

        setUpStage(mainStage);


    }

    private void setUpStage(Stage stage) {
        HBox rootContainer = new HBox();
        this.rootContainer = rootContainer;
        BorderPane contentHolder = new BorderPane();
        StackPane boardContainer = new StackPane(board);

        this.stage = stage;

        BorderPane.setAlignment(boardContainer, Pos.CENTER);

        contentHolder.setTop(getTopMenu());
        contentHolder.setCenter(boardContainer);

        BorderPane.setMargin(contentHolder.getTop(), new Insets(5, 0, 5, 5));

        rootContainer.getStylesheets().add("/menustyle.css");
        rootContainer.getChildren().add(contentHolder);

        stage.setTitle("Board");
        stage.setScene(new Scene(rootContainer));
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();

    }

    public void endGame() {
        rootContainer.getChildren().add(createEndDialog());

    }

    public HBox getTopMenu() {
        HBox container = new HBox();
        container.setAlignment(Pos.TOP_LEFT);
        Button homeBtn = new Button();
        homeBtn.setPrefSize(50, 50);
        homeBtn.getStyleClass().add("home-btn");
        homeBtn.setOnAction(e -> {
            try {
                new MainMenu().start(stage);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        container.getChildren().add(homeBtn);

        return container;
    }

    private GridPane createEndDialog() {
        GridPane root = new GridPane();
        root.getStylesheets().add("menustyle.css");
        root.setMaxHeight(stage.getHeight() / 4);
        root.setMaxWidth(stage.getWidth() / 2);
        root.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, new CornerRadii(5), Insets.EMPTY)));
        root.setHgap(20);
        root.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5))));

        Button retryBtn = new Button("Play Again");
        Button mainMenuBtn = new Button("Main Menu");
        Label status = new Label("Status!");


        retryBtn.setOnAction(event -> {
            try {
                this.start(this.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mainMenuBtn.setOnAction(event -> {
            try {
                new MainMenu().start(this.stage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        retryBtn.getStyleClass().addAll("accept-btn", "end-menu");
        mainMenuBtn.getStyleClass().addAll("cancel-btn", "end-menu");
        status.getStyleClass().add("end-menu-label");

        GridPane.setColumnSpan(status, 2);
        GridPane.setHalignment(status, HPos.CENTER);
        GridPane.setValignment(status, VPos.TOP);
        GridPane.setMargin(status, new Insets(2, 0, 60, 0));
        root.setAlignment(Pos.CENTER);
        root.add(retryBtn, 0, 1);
        root.add(mainMenuBtn, 1, 1);
        root.add(status, 0, 0);



        return root;
    }


    public static void main(String[] args) {
        launch(args);
    }

}
