package checkers.client.scenes;


import checkers.client.game.*;
import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.lang.reflect.Constructor;


public class GameLoop extends Application {


    Player player1;
    Player player2;
    Board board;
    Stage stage;
    Pane boardContainer;


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
        BorderPane contentHolder = new BorderPane();
        StackPane boardContainer = new StackPane(board);

        this.boardContainer = boardContainer;
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

    public void endGame(String msg) {
        boardContainer.getChildren().add(createEndDialog(msg));

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

    private GridPane createEndDialog(String msg) {
        GridPane root = new GridPane();
        root.getStylesheets().add("menustyle.css");
        root.setMaxHeight(stage.getHeight() / 4);
        root.setMaxWidth(stage.getWidth() / 2);
        root.setBackground(new Background(new BackgroundFill(Color.FIREBRICK, new CornerRadii(5), Insets.EMPTY)));
        root.setHgap(20);
        root.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(5))));

        Button retryBtn = new Button("Play Again");
        Button mainMenuBtn = new Button("Main Menu");
        Label status = new Label(msg);


        retryBtn.setOnAction(event -> {
            try {
                board = new Board();
                player1 = resetPlayer(player1);
                player2 = resetPlayer(player2);
                this.start(stage);
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
        GridPane.setMargin(retryBtn, new Insets(0, 0, 0, 20));
        GridPane.setHalignment(retryBtn, HPos.CENTER);
        GridPane.setHalignment(mainMenuBtn, HPos.CENTER);
        root.setAlignment(Pos.CENTER);
        root.add(retryBtn, 0, 1);
        root.add(mainMenuBtn, 1, 1);
        root.add(status, 0, 0);


        return root;
    }


    private Player resetPlayer(Player player) throws Exception {
        Class<?> playerClass = Class.forName(player.getClass().getName());
        Constructor<?> ctor = playerClass.getConstructor(Piece.PieceColor.class, int.class, Board.class);

        player = (Player) ctor.newInstance(player.getPlayerColor(), player.getPlayerNumber(), board);

        return player;

    }

    public static void main(String[] args) {
        launch(args);
    }

}
