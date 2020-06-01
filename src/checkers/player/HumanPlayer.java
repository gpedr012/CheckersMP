package checkers.player;

import checkers.ui.Piece;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HumanPlayer implements Player
{
    private List<Piece> pieces = new ArrayList<>(12);
    private int playerNumber;
    private Piece.PieceColor playerColor;

    public HumanPlayer(int playerNumber, Piece.PieceColor playerColor)
    {
        this.playerNumber = playerNumber;
        this.playerColor = playerColor;
    }

    @Override
    public void addPiece(Piece piece)
    {
        pieces.add(piece);
    }

    @Override
    public List<Piece> getPiecesWithMoves()
    {
        List<Piece> piecesWithMoves = new Stack<>();
        for (Piece piece: pieces
             )
        {

        }
        return null;
    }

    @Override
    public boolean hasPiecesLeft()
    {
        return pieces.isEmpty();
    }

    @Override
    public int getPlayerNumber()
    {
        return playerNumber;
    }

    @Override
    public Piece.PieceColor getPlayerColor()
    {
        return playerColor;
    }
}
