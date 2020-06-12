package checkers.player;

import checkers.ui.Board;
import checkers.ui.Piece;
import javafx.beans.binding.BooleanBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class HumanPlayer extends Player
{
    private List<Piece> pieces = new ArrayList<>(12);
    private int playerNumber;
    private Piece.PieceColor playerColor;
    private Board board;
    private MoveCalculator moveCalculator;


    public HumanPlayer(List<Piece> piecesList, int playerNumber)
    {
        super(piecesList, playerNumber);
    }
}
