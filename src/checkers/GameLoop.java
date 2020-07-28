package checkers;

import checkers.player.EasyAI;
import checkers.player.HumanPlayer;
import checkers.player.MoveList;
import checkers.player.Player;
import checkers.ui.Board;
import checkers.ui.Piece;
import checkers.ui.Tile;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GameLoop extends Application
{


    Player player1;
    Player player2;
    Board board;
    Scene scene;
    Stage stage;

    public GameLoop(Board board, Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;

    }

    @Override
    public void start(Stage stage) throws Exception
    {
        GameManager gameManager = new GameManager(board, player1, player2);
        HBox rootContainer = new HBox();
        BorderPane contentHolder = new BorderPane();
        HBox boardContainer = new HBox(board);
        this.stage = stage;

        boardContainer.setMaxSize(0,0);
        boardContainer.setAlignment(Pos.CENTER);
        boardContainer.setStyle("-fx-border-color: #5c2a0c; -fx-border-width: 20px");

        contentHolder.setTop(getTopMenu());
        contentHolder.setCenter(boardContainer);
        BorderPane.setMargin(contentHolder.getTop(), new Insets(5, 0, 5, 5));

        rootContainer.getStylesheets().add("/checkers/menustyle.css");
        rootContainer.getChildren().add(contentHolder);



        stage.setTitle("Board");
        stage.setScene(new Scene(rootContainer));
        stage.sizeToScene();
        stage.show();

        gameManager.startGame();



    }


    public HBox getTopMenu()
    {
        HBox container = new HBox();
        container.setAlignment(Pos.TOP_LEFT);
        Button homeBtn = new Button();
        homeBtn.setPrefSize(50,50);
        homeBtn.setId("home-btn");
        homeBtn.setOnAction(e -> {
            try
            {
                new MainMenu().start(stage);
            } catch (Exception exception)
            {
                exception.printStackTrace();
            }
        });

        container.getChildren().add(homeBtn);

        return container;
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
