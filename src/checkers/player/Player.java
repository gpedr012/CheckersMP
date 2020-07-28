package checkers.player;

import checkers.ui.Board;
import checkers.ui.Piece;
import checkers.ui.Tile;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public abstract class Player
{


    private final Piece.PieceColor playerColor;
    private final int playerNumber;
    private BooleanProperty hasTurn = new SimpleBooleanProperty(false);
    protected ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<>();
    protected List<Piece> piecesList;
    private MoveCalculator moveCalculator;
    private Board board;


    public Player(Piece.PieceColor playerColor, int playerNumber, Board board)
    {
        this.playerColor = playerColor;
        this.playerNumber = playerNumber;
        this.board = board;
        this.moveCalculator = new MoveCalculator(board, playerNumber, playerColor);

        piecesList = new ArrayList<>(12);
        initPieces();
        System.out.println(piecesList.size());
        board.addPiecesToBoard(this);
        System.out.println(piecesList.size());
    }

    /**
     * Cleans up the list of pieces (removes any eliminated pieces from  the list
     * Then clears the current movelist for each piece.
     * Scans through the list of pieces, and if it has required priority then the list is immediately assigned.
     *
     * Otherwise if no required moves were found it assigns them all to each piece.
     *
     * This way if there's a required move, only other required moves are allowed to be executed and non required are not
     * processed so they do not exist.
     */
    public void calculatePossibleMoves()
    {
        cleanUpPiecesList();
        List<MoveList> possibleMoves = new ArrayList<>(12);
        boolean foundRequired = false;
        for (int i = 0; i < piecesList.size(); i++)
        {
            piecesList.get(i).getPossibleMoves().clear();
            MoveList newMoveList = moveCalculator.getMoves(piecesList.get(i));
            if (newMoveList.getPriority() == MoveList.MovePriority.REQUIRED)
            {
                foundRequired = true;
                piecesList.get(i).setPossibleMoves(newMoveList);
            } else
            {
                possibleMoves.add(newMoveList);
            }
        }

        if (!foundRequired)
        {
            for (int i = 0; i < piecesList.size(); i++)
            {
                piecesList.get(i).setPossibleMoves(possibleMoves.get(i));

            }

        }
    }

    private void cleanUpPiecesList()
    {
        piecesList.removeIf(Piece::isEliminated);
    }

    public void addPiece(Piece piece)
    {
        piecesList.add(piece);
    }

    public List<Piece> getPiecesWithMoves()
    {
        List<Piece> piecesWithMoves = new ArrayList<>();
        for (Piece piece : piecesList)
        {
            if (piece.hasAnyMoves())
            {
                piecesWithMoves.add(piece);
            }
        }

        return piecesWithMoves;
    }

    public abstract void processTurn();

    public abstract void endTurn();

    public boolean hasPiecesLeft()
    {
        return piecesList.isEmpty();
    }

    public int getPlayerNumber()
    {
        return playerNumber;
    }

    public Piece.PieceColor getPlayerColor()
    {
        return playerColor;
    }

    public boolean getHasTurn()
    {
        return hasTurn.get();
    }

    public BooleanProperty hasTurnProperty()
    {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn)
    {
        this.hasTurn.set(hasTurn);
    }

    private void initPieces()
    {
        final int MAX_PIECES = 12;
        for (int i = 0; i < MAX_PIECES; i++)
        {
            this.addPiece(new Piece(this.playerColor));
        }

    }


    public Piece getPiece(int index)
    {
        return piecesList.get(index);
    }

    protected Board getBoard()
    {
        return board;
    }

    protected void playMovementAnimation(Tile currentTile, Tile destinationTile)
    {
        Piece piece = currentTile.getPiece();

        Bounds currentPosition = currentTile.localToParent(currentTile.getBoundsInLocal());
        Bounds destinationPosition = destinationTile.localToParent(destinationTile.getBoundsInLocal());

        TranslateTransition animation = createTranslateTransition(piece, currentPosition, destinationPosition);
        currentTile.removePiece();
        board.getChildren().add(piece);
        animation.play();
        animation.setOnFinished(event -> finishAnimation(piece, destinationTile));

    }

    private void finishAnimation(Piece piece, Tile destination)
    {
        board.getChildren().remove(piece);
        destination.addPiece(piece);
        piece.setTranslateX(0);
        piece.setTranslateY(0);
        if(piece.getPossibleMoves().getPriority() == MoveList.MovePriority.REQUIRED)
        {
            piece.getPossibleMoves().getOpponentTile().eliminatePiece();
        }
        if((destination.getRow() == 0 || destination.getRow() == 7) && !piece.isCrowned())
            piece.setCrowned(true);

        piece.setRow(destination.getRow());
        piece.setCol(destination.getCol());

        endTurn();
        System.out.println("finished animation.");

    }

    private TranslateTransition createTranslateTransition(Piece pieceToAnimate, Bounds currentPosition, Bounds destinationPosition)
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
