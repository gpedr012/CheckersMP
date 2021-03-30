package checkers.client.game;

import checkers.client.ui.Animator;
import checkers.client.ui.Board;
import checkers.client.ui.Tile;
import checkers.networkutils.Action;
import checkers.networkutils.Message;

public class OnlineGameManager {
    private static Board board;
    private static Player player;


    private OnlineGameManager() {
    }


    public static void processPlayerTurn() {
        player.setHasTurn(true);
        player.processTurn();

    }

    public static void initialize(Board board, Player player) {
        OnlineGameManager.board = board;
        OnlineGameManager.player = player;

    }

    public static void processEnemyTurn(Action action) {
        int[] actionArgs = action.getAllArgs();

        int rowOrigin = actionArgs[Message.ROW_ORIGIN_IDX];
        int colOrigin = actionArgs[Message.COL_ORIGIN_IDX];
        int rowDest = actionArgs[Message.ROW_DEST_IDX];
        int colDest = actionArgs[Message.COL_DEST_IDX];

        Tile currentTile = board.getTile(rowOrigin, colOrigin);
        Tile destinationTile = board.getTile(rowDest, colDest);

        if (action.getType() == Action.Type.MOVE_ELIM) {
            int enemyRow = actionArgs[Message.ROW_ELIM_IDX];
            int enemyCol = actionArgs[Message.COL_ELIM_IDX];

            Tile enemyTile = board.getTile(enemyRow, enemyCol);

            Animator.playEnemyMovementAnimation(board, currentTile, destinationTile, enemyTile);

        } else {
            Animator.playEnemyMovementAnimation(board, currentTile, destinationTile);

        }
    }
}
