package checkers.player;

import checkers.ui.Board;
import checkers.ui.Piece;
import checkers.ui.Tile;
import javafx.animation.TranslateTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Bounds;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public abstract class Player
{


    private final Piece.PieceColor playerColor;
    private final int playerNumber;
    private BooleanProperty hasTurn = new SimpleBooleanProperty(false);
    protected ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<>();
    protected List<Piece> piecesList;
    private Board board;


    public Player(Piece.PieceColor playerColor, int playerNumber, Board board)
    {
        this.playerColor = playerColor;
        this.playerNumber = playerNumber;
        this.board = board;

        piecesList = new ArrayList<>(12);
        initPieces();
        board.addPiecesToBoard(this);
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
            if(piece.hasAnyMoves())
            {
                piecesWithMoves.add(piece);
            }
        }

        return piecesWithMoves;
    }

    public abstract void processTurn();

    public void endTurn()
    {
        hasTurn.set(false);
    }

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

    protected void playMovementAnimation(Tile currentTile, Tile destinationTile)
    {
        Piece piece = currentTile.getPiece();

        Bounds currentPosition = currentTile.localToScene(currentTile.getBoundsInLocal());
        Bounds destinationPosition = destinationTile.localToScene(destinationTile.getBoundsInLocal());

        TranslateTransition animation = createTranslateTransition(piece, currentPosition, destinationPosition);
        currentTile.removePiece();
        board.getChildren().add(piece);
        animation.setOnFinished(e -> { finishAnimation(piece, destinationTile);});
        animation.play();



    }

    private void finishAnimation(Piece piece, Tile destination)
    {

        System.out.println("destination.containsPiece() = " + destination.containsPiece());
        destination.addPiece(piece);
        System.out.println("destination.containsPiece() = " + destination.containsPiece());
        endTurn();

    }

    private TranslateTransition createTranslateTransition(Piece pieceToAnimate, Bounds currentPosition, Bounds destinationPosition)
    {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(1.5), pieceToAnimate);
        double offset = Tile.SIDE_LENGTH / 2d;

        transition.setCycleCount(1);
        transition.setFromX(currentPosition.getCenterX() - offset);
        transition.setFromY(currentPosition.getCenterY() - offset);

        transition.setToX(destinationPosition.getCenterX() - offset);
        transition.setToY(destinationPosition.getCenterY() - offset);

        return transition;

    }


}
