package checkers.client.game;

import checkers.client.scenes.GameLoop;
import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.Stack;

public class GameManager {
    private final Board board;
    private final Player playerOne;
    private final Player playerTwo;
    private final GameLoop gameBeingManaged;
    private Stack<Move> moveStack = new Stack<>();

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

    public void undoMove() {

        if(playerTwo instanceof EasyAI && playerOne.getHasTurn() && !board.isMoveStackEmpty()) {
            restorePreviousMoveState(board.popMove());
            restorePreviousMoveState(board.popMove());

            playerOne.setSelectedPiece(null);
            playerOne.processTurn();

        } else if (playerTwo instanceof HumanPlayer && !board.isMoveStackEmpty()) {
            restorePreviousMoveState(board.popMove());

            if(playerOne.getHasTurn()) {
                playerOne.endTurn();
                playerTwo.setHasTurn(true);
            } else {
                playerTwo.endTurn();
                playerOne.setHasTurn(true);
            }
        }

    }

    private void restorePreviousMoveState(Move move) {
        Tile originalTile = move.getOriginalTile();
        Tile destinationTile = move.getMovementTile();

        Piece pieceThatMoved = destinationTile.getPiece();

        destinationTile.removePiece();
        originalTile.addPiece(pieceThatMoved);

        pieceThatMoved.setRow(originalTile.getRow());
        pieceThatMoved.setCol(originalTile.getCol());

        if (move.getOpponentTile() != null) {
            Piece.PieceColor pieceColor = pieceThatMoved.getColor();
            Player oppPlayer = pieceColor == playerOne.getPlayerColor() ? playerOne : playerTwo;
            Piece replacementPiece = new Piece(oppPlayer.getPlayerColor());

            Tile oppTile = move.getOpponentTile();
            oppTile.addPiece(replacementPiece);
            oppPlayer.addPiece(replacementPiece);

        }

    }

    private String constructEndMsg(Player winner) {
        Piece.PieceColor winnerColor = winner.getPlayerColor();
        return String.format("%s pieces win!", winnerColor.toString());

    }

}
