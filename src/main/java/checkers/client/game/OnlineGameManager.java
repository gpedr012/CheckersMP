package checkers.client.game;

import checkers.client.ui.Board;

public class OnlineGameManager
{
    private static Board board;
    private static Player player;

    private OnlineGameManager () {}


    public static void updateTurnStatus(boolean turnStatus) {
        player.setHasTurn(turnStatus);

        if(turnStatus) {
            player.processTurn();

        }

    }

    public static void initialize(Board board, Player player) {
        OnlineGameManager.board = board;
        OnlineGameManager.player = player;

    }
}
