package checkers.client.game;

import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;

import java.util.List;

public class EasyAI extends Player
{

    public EasyAI(Piece.PieceColor playerColor, int playerNumber, Board board)
    {
        super(playerColor, playerNumber, board);

    }

    @Override
    public void processTurn()
    {

        calculatePossibleMoves();
        List<Piece> piecesWithMoves =  getPiecesWithMoves();


        int piecesSize = piecesWithMoves.size();
        int pieceToMoveIndex = (int)(Math.random() * piecesSize);
        Piece pieceToMove = piecesWithMoves.get(pieceToMoveIndex);

        int movesSize = pieceToMove.getPossibleMoves().size();
        int moveIndex = (int)(Math.random() * movesSize);
        Tile destinationTile = pieceToMove.getPossibleMoves().get(moveIndex);

        Tile currentTile = getBoard().getTile(pieceToMove.getRow(), pieceToMove.getCol());
        playMovementAnimation(currentTile, destinationTile);

    }

    @Override
    public void endTurn()
    {
        setHasTurn(false);
    }
}
