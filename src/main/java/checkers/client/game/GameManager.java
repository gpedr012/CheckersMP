package checkers.client.game;

import checkers.client.scenes.GameLoop;
import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class GameManager {
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;
    private final GameLoop gameBeingManaged;

    public GameManager(Board board, Player playerOne, Player playerTwo, GameLoop gameBeingManaged) {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.gameBeingManaged = gameBeingManaged;
        initListeners();
    }

    public void startGame() {
        playerOne.setHasTurn(true);


    }

    private void initListeners() {
        playerOne.hasTurnProperty().addListener((observableValue, oldV, newV) -> {
            if (newV) {

                if (playerOne.hasPiecesLeft()) {
                    playerOne.processTurn();

                } else {
                    String endingMsg = constructEndMsg(playerTwo);
                    gameBeingManaged.endGame(endingMsg);

                }

            } else {
                playerTwo.setHasTurn(true);

            }
        });

        playerTwo.hasTurnProperty().addListener((observableValue, oldV, newV) -> {
            if (newV) {
                if (playerTwo.hasPiecesLeft()) {
                    playerTwo.processTurn();

                } else {
                    String endingMsg = constructEndMsg(playerOne);
                    gameBeingManaged.endGame(endingMsg);

                }

            } else {
                playerOne.setHasTurn(true);

            }
        });


    }

    private String constructEndMsg(Player winner) {
        Piece.PieceColor winnerColor = winner.getPlayerColor();
        return String.format("%s pieces win!", winnerColor.toString());

    }

}
