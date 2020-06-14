package checkers.player;

import checkers.ui.Piece;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class Player
{

    private List<Piece> piecesList;
    private final Piece.PieceColor playerColor;
    private final int playerNumber;
    private BooleanProperty hasTurn = new SimpleBooleanProperty(false);


    public Player(Piece.PieceColor playerColor, int playerNumber)
    {
        this.playerColor = playerColor;
        this.playerNumber = playerNumber;
        piecesList = new ArrayList<>(12);
        initPieces();
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

    protected List<Piece> getPiecesList()
    {
        return piecesList;
    }

    public Piece getPiece(int index)
    {
        return piecesList.get(index);
    }


}
