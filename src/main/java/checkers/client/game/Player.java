package checkers.client.game;

import checkers.client.ui.Animator;
import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class Player {


    private final Piece.PieceColor playerColor;
    private final int playerNumber;
    private final BooleanProperty hasTurn = new SimpleBooleanProperty(false);
    private ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<>();
    protected List<Piece> piecesList;
    private final MoveCalculator moveCalculator;
    private final Board board;


    public Player(Piece.PieceColor playerColor, int playerNumber, Board board) {

        this.playerColor = playerColor;
        this.playerNumber = playerNumber;
        this.board = board;
        this.moveCalculator = new MoveCalculator(board, playerNumber, playerColor);

        piecesList = new ArrayList<>(12);
        initPieces();
        board.addPiecesToBoard(this);
    }

    /**
     * Cleans up the list of pieces (removes any eliminated pieces from  the list
     * Then clears the current movelist for each piece.
     * Scans through the list of pieces, and if it has required priority then the list is immediately assigned.
     * <p>
     * Otherwise if no required moves were found it assigns them all to each piece.
     * <p>
     * This way if there's a required move, only other required moves are allowed to be executed and non required are not
     * processed so they do not exist.
     */
    public void calculatePossibleMoves() {

        cleanUpPiecesList();
        List<MoveList> possibleMoves = new ArrayList<>(12);
        boolean foundRequired = false;
        for (int i = 0; i < piecesList.size(); i++) {
            piecesList.get(i).getPossibleMoves().clear();
            MoveList newMoveList = moveCalculator.getMoves(piecesList.get(i));
            if (newMoveList.getPriority() == MoveType.REQUIRED) {
                foundRequired = true;
                piecesList.get(i).setPossibleMoves(newMoveList);
            } else {
                possibleMoves.add(newMoveList);
            }
        }

        if (!foundRequired) {
            for (int i = 0; i < piecesList.size(); i++) {
                piecesList.get(i).setPossibleMoves(possibleMoves.get(i));

            }

        }
    }

    private void cleanUpPiecesList() {
        piecesList.removeIf(Piece::isEliminated);
    }

    public void addPiece(Piece piece) {
        piecesList.add(piece);
    }

    public List<Piece> getPiecesWithMoves() {
        List<Piece> piecesWithMoves = new ArrayList<>();
        for (Piece piece : piecesList) {
            if (piece.hasAnyMoves()) {
                piecesWithMoves.add(piece);
            }
        }

        return piecesWithMoves;
    }

    public abstract void processTurn();

    public abstract void endTurn();

    public boolean hasPiecesLeft() {
        cleanUpPiecesList();
        return !piecesList.isEmpty();
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Piece.PieceColor getPlayerColor() {
        return playerColor;
    }

    public boolean getHasTurn() {
        return hasTurn.get();
    }

    public BooleanProperty hasTurnProperty() {
        return hasTurn;
    }

    public void setHasTurn(boolean hasTurn) {
        this.hasTurn.set(hasTurn);
    }

    private void initPieces() {
        final int MAX_PIECES = 12;
        for (int i = 0; i < MAX_PIECES; i++) {
            this.addPiece(new Piece(this.playerColor));

        }

    }

    public Piece getSelectedPiece() {
        return selectedPiece.get();
    }

    public ObjectProperty<Piece> selectedPieceProperty() {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece) {
        this.selectedPiece.set(selectedPiece);
    }

    @Override
    public String toString() {
        return String.format("PlayerColor:  %s <-> PlayerNumber: %d <-> Board: %s", playerColor.toString(), playerNumber, board.toString());
    }

    public Piece getPiece(int index) {
        return piecesList.get(index);
    }

    protected Board getBoard() {
        return board;
    }

    protected void playMovementAnimation(Tile currentTile, Tile destinationTile) {
        Animator.playMovementAnimation(this, board, currentTile, destinationTile);

    }
}
