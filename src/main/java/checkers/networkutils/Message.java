package checkers.networkutils;

import javafx.scene.control.Alert;

public class Message {

    public static final String PLAYER_MSG_PREFIX = "[PLAYER]";
    public static final String SERVER_INFO_MSG_PREFIX = "[SERVER_INFO]";
    public static final String INTERNAL_SERVER_MSG_PREFIX = "[INTERNAL]";

    public static final String FIND_MATCH_MSG = "queue";
    public static final String CANCEL_MATCH_MSG = "cancel_queue";

    public static final String MSG_DELIMITER = "\n";
    public static final String MSG_CONTENT_DELIMITER = ":";

    private Message () {}

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

    public static String createInternalServerMsg(String content) {
        return formatMessage(INTERNAL_SERVER_MSG_PREFIX, content);
    }

    public static String cancelMatchMakingMsg() {

        return formatMessage(PLAYER_MSG_PREFIX, CANCEL_MATCH_MSG);

    }

    private static String formatMessage(String header, String content) {

        return header + MSG_CONTENT_DELIMITER + content + MSG_DELIMITER;

    }

}
