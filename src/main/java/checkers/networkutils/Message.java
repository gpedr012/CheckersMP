package checkers.networkutils;

import javafx.css.Match;
import javafx.scene.control.Alert;

public class Message {

    public static final String PLAYER_MSG_PREFIX = "[PLAYER]";
    public static final String SERVER_INFO_MSG_PREFIX = "[SERVER_INFO]";
    public static final String INTERNAL_SERVER_MSG_PREFIX = "[INTERNAL]";

    public static final String FIND_MATCH_MSG = "queue";
    public static final String CANCEL_MATCH_MSG = "cancelQueue";

    //syntax match=id where id is the id assigned to the match by the server. Used by both client & server to communicate.
    public static final String MATCH_FOUND_MSG = "match=";

    public static final String HAS_TURN_MSG = "hasTurn";

    public static final String MATCH_WON = "matchWon";

    public static final String MATCH_LOST = "matchLost";

    //syntax matchMove=id-row1-col1-row2-col2-row3-col3 where id is matchId and row & col position of movement.
    //row3 col3 may be empty if no enemy piece was eliminated.
    public static final String MATCH_MOVE_MSG = "matchMove=";

    public static final int MATCH_ID_IDX = 0;
    public static final int ROW_ORIGIN_IDX = 1;
    public static final int COL_ORIGIN_IDX = 2;
    public static final int ROW_DEST_IDX = 3;
    public static final int COL_DEST_IDX = 4;
    public static final int ROW_ELIM_IDX = 5;
    public static final int COL_ELIM_IDX = 6;

    public static final String MSG_DELIMITER = "\n";
    public static final String MSG_CONTENT_DELIMITER = ":";

    private Message() {
    }

    public static String createFindMatchMsg() {
        return formatMessage(PLAYER_MSG_PREFIX, FIND_MATCH_MSG);

    }

    public static Alert createConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Error");
        alert.setHeaderText("Unable to connect to server");
        alert.setContentText("Server is unreachable. Either your connection or the server is offline.");
        return alert;

    }

    public static String createServerInfoMsg(String content) {
        return formatMessage(SERVER_INFO_MSG_PREFIX, content);

    }

    public static String createMatchFoundMsg(int matchId, int colorId) {
        return formatMessage(INTERNAL_SERVER_MSG_PREFIX, MATCH_FOUND_MSG + String.format("%d,%d", matchId, colorId));

    }

    public static String createCancelMatchMakingMsg() {
        return formatMessage(PLAYER_MSG_PREFIX, CANCEL_MATCH_MSG);

    }

    public static String createMatchMoveMsg(int matchId, int rowOrigin, int colOrigin, int rowDest, int colDest) {

        return formatMessage(INTERNAL_SERVER_MSG_PREFIX, MATCH_MOVE_MSG + String.format("%d-%d-%d-%d-%d", matchId, rowOrigin, colOrigin, rowDest, colDest));

    }

    public static String createMatchMoveMsg(int matchId, int rowOrigin, int colOrigin, int rowDest, int colDest, int enemyRow, int enemyCol) {

        return formatMessage(INTERNAL_SERVER_MSG_PREFIX, MATCH_MOVE_MSG + String.format("%d-%d-%d-%d-%d-%d-%d", matchId, rowOrigin, colOrigin, rowDest, colDest, enemyRow, enemyCol));
    }

    public static String createHasTurnMsg() {
        return formatMessage(INTERNAL_SERVER_MSG_PREFIX, HAS_TURN_MSG);

    }

    private static String formatMessage(String header, String content) {
        return header + MSG_CONTENT_DELIMITER + content + MSG_DELIMITER;

    }

}
