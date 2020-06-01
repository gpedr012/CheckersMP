package checkers;

import checkers.player.HumanPlayer;
import checkers.ui.Board;
import checkers.ui.Piece;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        AnchorPane root = new AnchorPane();
        root.getChildren().add(new Board(new HumanPlayer(1, Piece.PieceColor.LIGHT), new HumanPlayer(2, Piece.PieceColor.DARK)));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
