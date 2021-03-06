package checkers.networkutils;

import javafx.scene.control.Alert;

public class Message {

    public static final String PLAYER_MSG_PREFIX = "[PLAYER]";
    public static final String SERVER_INFO_MSG_PREFIX = "[SERVER_INFO]";

    public static final String FIND_MATCH_MSG = "queue";

    public static final String MSG_DELIMITER = "\n";
    public static final String MSG_CONTENT_DELIMITER = ":";

    private Message () {}

    public static String createFindMatchMsg() {

        return PLAYER_MSG_PREFIX + MSG_CONTENT_DELIMITER + FIND_MATCH_MSG + MSG_DELIMITER;

    }

    public static Alert createConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Error");
        alert.setHeaderText("Unable to connect to server");
        alert.setContentText("Server is unreachable. Either your connection or the server is offline.");
        return alert;
    }

    public static String createServerInfoMsg(String content) {
        return SERVER_INFO_MSG_PREFIX + MSG_CONTENT_DELIMITER + content + MSG_DELIMITER;

    }

}
