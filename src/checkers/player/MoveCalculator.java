package checkers.player;

import checkers.ui.Board;
import checkers.ui.Piece;
import checkers.ui.Tile;

import java.util.List;
import java.util.Stack;

public class MoveCalculator
{

    private final Board board;
    private final int playerNumber;
    private final Piece.PieceColor playerColor;

    private enum MoveType
    {
        OPTIONAL, REQUIRED, IMPOSSIBLE
    }

    public MoveCalculator(Board board, int playerNumber, Piece.PieceColor playerColor)
    {
        this.board = board;
        this.playerNumber = playerNumber;
        this.playerColor = playerColor;

    }

    public void calculateAllMoves(List<Piece> pieces)
    {
        for(Piece piece: pieces)
        {
            piece.setPossibleMoves(getMoves(piece));
        }
    }

    public MoveList getMoves(Piece piece)
    {
        int currentRow = piece.getRow();
        int currentCol = piece.getCol();


        MoveList possibleMoves;
        if (piece.isCrowned())
        {
            possibleMoves = findPossibleMoves(currentRow, currentCol, -1);
            possibleMoves.addAll(findPossibleMoves(currentRow, currentCol, 1));
        } else
        {
            int rowModifier = playerNumber == 1 ? -1 : 1;
            possibleMoves = findPossibleMoves(currentRow, currentCol, rowModifier);
        }

        return possibleMoves;
    }


    private MoveList findPossibleMoves(int currentRow, int currentCol, int rowModifier)
    {
        Stack<Move> moveTypeStack = new Stack<>();
        MoveList moveList;

        int rowToCheck = currentRow + rowModifier;

        if (!validRowOrCol(rowToCheck))
        {
            return new MoveList();

        }

        if (validRowOrCol(currentCol + 1))
        {
            Move possibleMove = new Move(rowToCheck, currentCol + 1);
            possibleMove.assignType(rowModifier, 1);


        }
        if (validRowOrCol(currentCol - 1))
        {
            Move possibleMove = new Move(rowToCheck, currentCol - 1);
            possibleMove.assignType(rowModifier, -1);
        }

        return processMoves(moveTypeStack);
    }


    private MoveList processMoves(Stack<Move> moveTypeStack)
    {
        MoveList moveList = new MoveList();

        for (Move move : moveTypeStack
        )
        {
            switch (move.getType())
            {
                case REQUIRED:
                    moveList.clear();
                    moveList.addMove(move.getTile());
                    return moveList;
                case OPTIONAL:
                    moveList.addMove(move.getTile());
                    break;
                case IMPOSSIBLE:
                    continue;
                default:
                    throw new RuntimeException("Defaulted in switch inside processMoves in MoveCalculator.");

            }
        }

        return moveList;
    }

    private class Move
    {
        private int row;
        private int col;
        private MoveType type;

        public Move(int row, int col)
        {
            this.row = row;
            this.col = col;

        }

        public int getRow()
        {
            return row;
        }

        public int getCol()
        {
            return col;
        }

        public MoveType getType()
        {
            return type;
        }

        public Tile getTile()
        {
            return board.getTile(row, col);
        }

        public void assignType(int rowModifier, int colModifier)
        {
            Tile moveTile = board.getTile(row, col);

            if (moveTile.containsPiece())
            {
                if (moveTile.getPiece().getColor() != playerColor && canJumpToTile(row + rowModifier, col + colModifier))
                {
                    type = MoveType.REQUIRED;
                    row += rowModifier;
                    col += colModifier;
                } else
                {
                    type = MoveType.IMPOSSIBLE;
                }
            } else
            {
                type = MoveType.OPTIONAL;
            }
        }

        private boolean canJumpToTile(int row, int col)
        {
            return validRowOrCol(row) && validRowOrCol(col) && !board.getTile(row, col).containsPiece();
        }
    }

    private boolean validRowOrCol(int i)
    {
        return i >= 0 && i < 8;
    }

}
