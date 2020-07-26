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
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


public class GameLoop extends Application
{


    Player player1;
    Player player2;
    Board board;
    Scene scene;

    public GameLoop(Board board, Player player1, Player player2)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.board = board;

    }

    @Override
    public void start(Stage stage) throws Exception
    {

        AnchorPane root = new AnchorPane();

        GameManager gameManager = new GameManager(board, player1, player2);

        root.getChildren().add(board);
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 640, 640));
        stage.show();

        gameManager.startGame();



    }



    public static void main(String[] args)
    {
        launch(args);
    }
}
