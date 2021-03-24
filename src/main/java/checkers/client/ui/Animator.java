package checkers.client.ui;

import checkers.client.game.MoveList;
import checkers.client.game.Player;
import checkers.client.network.ClientNetworkHelper;
import checkers.networkutils.Message;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.util.Duration;

public class Animator {

    private Animator() {}

//    public static void playMovementAnimation(Player player, Board board, Tile currentTile, Tile destinationTile)
//    {
//        Piece piece = currentTile.getPiece();
//
//        Bounds currentPosition = currentTile.localToParent(currentTile.getBoundsInLocal());
//        Bounds destinationPosition = destinationTile.localToParent(destinationTile.getBoundsInLocal());
//
//        TranslateTransition animation = createTranslateTransition(piece, currentPosition, destinationPosition);
//        currentTile.removePiece();
//        board.getChildren().add(piece);
//        animation.play();
//        animation.setOnFinished(event -> finishAnimation(player, board, piece, destinationTile));
//
//
//    }

    public static void playMovementAnimation(Player player, Board board, Tile currentTile, Tile destinationTile)
    {
        Piece piece = currentTile.getPiece();
        EventHandler<ActionEvent> finishHandler = event -> finishAnimation(player, board, piece, destinationTile);
        initAnimation(board, currentTile, destinationTile, finishHandler);


    }

    public static void playMovementAnimation(Board board, Tile currentTile, Tile destinationTile)
    {
        Piece piece = currentTile.getPiece();
        EventHandler<ActionEvent> finishHandler = event -> finishAnimation(board, piece, destinationTile);
        initAnimation(board, currentTile, destinationTile, finishHandler);


    }

    public static void playMovementAnimation(Board board, Tile currentTile, Tile destinationTile, Tile enemyTile)
    {
        Piece piece = currentTile.getPiece();
        EventHandler<ActionEvent> finishHandler = event -> finishAnimation(board, piece, destinationTile);
        initAnimation(board, currentTile, destinationTile, finishHandler);


    }

    private static void initAnimation(Board board, Tile currentTile, Tile destinationTile, EventHandler<ActionEvent> finishHandler) {
        Piece piece = currentTile.getPiece();

        Bounds currentPosition = currentTile.localToParent(currentTile.getBoundsInLocal());
        Bounds destinationPosition = destinationTile.localToParent(destinationTile.getBoundsInLocal());

        TranslateTransition animation = createTranslateTransition(piece, currentPosition, destinationPosition);
        currentTile.removePiece();
        board.getChildren().add(piece);
        animation.play();
        animation.setOnFinished(finishHandler);
    }



    private static void finishAnimation(Player player, Board board, Piece piece, Tile destination)
    {
        finishAnimCleanup(board, piece, destination);

        if(piece.getPossibleMoves().getPriority() == MoveList.MovePriority.REQUIRED)
        {
            piece.getPossibleMoves().getOpponentTile().eliminatePiece();

        }

        player.endTurn();

    }

    private static void finishAnimation(Board board, Piece piece, Tile destination)
    {
        finishAnimCleanup(board, piece, destination);


    }

    private static void finishAnimation(Board board, Piece piece, Tile destination, Tile enemyTile)
    {
        finishAnimCleanup(board, piece, destination);

        enemyTile.eliminatePiece();


    }

    private static void finishAnimCleanup(Board board, Piece piece, Tile destination) {
        board.getChildren().remove(piece);
        destination.addPiece(piece);
        piece.setTranslateX(0);
        piece.setTranslateY(0);
        piece.setRow(destination.getRow());
        piece.setCol(destination.getCol());


        if((destination.getRow() == 0 || destination.getRow() == 7) && !piece.isCrowned())
            piece.setCrowned(true);
    }



    private static TranslateTransition createTranslateTransition(Piece pieceToAnimate, Bounds currentPosition, Bounds destinationPosition)
    {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), pieceToAnimate);
        double offset = Tile.SIDE_LENGTH / 2d;

        transition.setFromX(currentPosition.getCenterX() - offset);
        transition.setFromY(currentPosition.getCenterY() - offset);

        transition.setToX(destinationPosition.getCenterX() - offset);
        transition.setToY(destinationPosition.getCenterY() - offset);

        return transition;

    }

}
