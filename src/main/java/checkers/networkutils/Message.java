package checkers.networkutils;

import javafx.scene.control.Alert;

public class Message {

    private static final String PLAYER_MSG_PREFIX = "[PLAYER]:";

    private static final String FIND_MATCH_MSG = "queue";

    private static final String MSG_DELIMITER = "\n";

    private Message () {}

    public static String createFindMatchMsg() {

        return PLAYER_MSG_PREFIX + FIND_MATCH_MSG + MSG_DELIMITER;

    }

    public static Alert createConnectionErrorAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Connection Error");
        alert.setHeaderText("Unable to connect to server");
        alert.setContentText("Server is unreachable. Either your connection or the server is offline.");
        return alert;
    }

}
