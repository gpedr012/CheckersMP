package checkers.player;

import checkers.ui.Piece;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class Player
{

    List<Piece> piecesList;
    final Piece.PieceColor playerColor;
    final int playerNumber;
    BooleanProperty hasTurn = new SimpleBooleanProperty(false);

    public Player(List<Piece> piecesList, int playerNumber)
    {
        playerColor = piecesList.get(0).getColor();
        this.playerNumber = playerNumber;
        this.piecesList = piecesList;
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


}
