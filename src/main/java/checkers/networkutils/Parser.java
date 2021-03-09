package checkers.networkutils;

public class Parser {


    public Parser() {
    }

    public Action parseMessage(String msg) {


        if (msg.startsWith(Message.PLAYER_MSG_PREFIX)) {
            msg = msg.split(Message.MSG_CONTENT_DELIMITER)[1];

            if (msg.equals(Message.FIND_MATCH_MSG)) {
                return Action.FIND_MATCH;
            } else if (msg.equals(Message.CANCEL_MATCH_MSG)) {
                return Action.CANCEL_MM;
            }
        } else if (msg.startsWith(Message.SERVER_INFO_MSG_PREFIX)) {
            return Action.SERVER_INFO;
        }


        return null;
    }

}
