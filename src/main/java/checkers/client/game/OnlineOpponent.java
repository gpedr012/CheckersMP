package checkers.client.game;

import checkers.client.ui.Board;
import checkers.client.ui.Piece;

public class OnlineOpponent extends Player{


    public OnlineOpponent(Piece.PieceColor playerColor, int playerNumber, Board board) {
        super(playerColor, playerNumber, board);
    }

    @Override
    public void processTurn() {

    }

    @Override
    public void endTurn() {

    }
}
