package checkers.client.game;

import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;

import java.util.List;
import java.util.Stack;

public class MoveCalculator {

    private final Board board;
    private final int playerNumber;
    private final Piece.PieceColor playerColor;


    public MoveCalculator(Board board, int playerNumber, Piece.PieceColor playerColor) {
        this.board = board;
        this.playerNumber = playerNumber;
        this.playerColor = playerColor;

    }

    public MoveList getMoves(Piece piece) {

        int currentRow = piece.getRow();
        int currentCol = piece.getCol();


        MoveList possibleMoves;
        if (piece.isCrowned()) {
            possibleMoves = findPossibleMoves(currentRow, currentCol, -1);
            possibleMoves.addAll(findPossibleMoves(currentRow, currentCol, 1));
        } else {
            int rowModifier = playerNumber == 1 ? -1 : 1;
            possibleMoves = findPossibleMoves(currentRow, currentCol, rowModifier);
        }

        return possibleMoves;
    }


    private MoveList findPossibleMoves(int currentRow, int currentCol, int rowModifier) {
        Stack<Move> moveTypeStack = new Stack<>();
        MoveList moveList;

        int rowToCheck = currentRow + rowModifier;

        if (!validRowOrCol(rowToCheck)) {
            return new MoveList();

        }

        if (validRowOrCol(currentCol + 1)) {
            Move possibleMove = new Move(board.getTile(currentRow, currentCol), board.getTile(rowToCheck, currentCol + 1));
            assignType(possibleMove, rowModifier, 1);
            moveTypeStack.push(possibleMove);


        }
        if (validRowOrCol(currentCol - 1)) {
            Move possibleMove = new Move(board.getTile(currentRow, currentCol), board.getTile(rowToCheck, currentCol - 1));
            assignType(possibleMove, rowModifier, -1);
            moveTypeStack.push(possibleMove);
        }

        return processMoves(moveTypeStack);
    }


    private MoveList processMoves(Stack<Move> moveTypeStack) {
        MoveList moveList = new MoveList();


        for (Move move : moveTypeStack
        ) {
            switch (move.getPriorityType()) {
                case REQUIRED:
                    if (moveList.getPriority() != MoveType.REQUIRED) {
                        moveList.clear();

                    }
                    moveList.addMove(move);
                case OPTIONAL:

                    if (moveList.getPriority() != MoveType.REQUIRED) {
                        moveList.addMove(move);
                    }
                    break;
                case IMPOSSIBLE:
                    continue;
                default:
                    throw new RuntimeException("Defaulted in switch inside processMoves in MoveCalculator.");

            }
        }

        return moveList;
    }

    private void assignType(Move move, int rowModifier, int colModifier) {
        Tile moveTile = move.getMovementTile();
        int row = moveTile.getRow();
        int col = moveTile.getCol();


        if (moveTile.containsPiece()) {
            if (moveTile.getPiece().getColor() != playerColor && canJumpToTile(row + rowModifier, col + colModifier)) {
                move.setOpponentTile(moveTile);
                move.setType(checkers.client.game.MoveType.REQUIRED);
                move.setMovementTile(board.getTile(row + rowModifier, col + colModifier));

            } else {

                move.setType(checkers.client.game.MoveType.IMPOSSIBLE);
            }
        } else {
            move.setType(checkers.client.game.MoveType.OPTIONAL);
        }
    }

    private boolean canJumpToTile(int row, int col) {
        return validRowOrCol(row) && validRowOrCol(col) && !board.getTile(row, col).containsPiece();
    }

    private boolean validRowOrCol(int i) {
        return i >= 0 && i < 8;
    }

}
