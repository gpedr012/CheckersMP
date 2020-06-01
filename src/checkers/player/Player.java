package checkers.player;

import checkers.ui.Piece;

import java.util.List;

public interface Player
{

    void addPiece(Piece piece);

    List<Piece> getPiecesWithMoves();

    boolean hasPiecesLeft();

    int getPlayerNumber();

    Piece.PieceColor getPlayerColor();








}
