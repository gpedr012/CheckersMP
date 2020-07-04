package checkers;

import checkers.player.HumanPlayer;
import checkers.player.MoveList;
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


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnchorPane root = new AnchorPane();



        Board board = new Board();
        HumanPlayer player1 = new HumanPlayer(Piece.PieceColor.DARK, 1, board);
        HumanPlayer player2 = new HumanPlayer(Piece.PieceColor.LIGHT, 2, board);
        GameManager gameManager = new GameManager(board, player1, player2);


        root.getChildren().add(board);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        gameManager.startGame();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
