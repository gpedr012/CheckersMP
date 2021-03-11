package checkers.networkutils;

public class Parser {


    public Parser() {
    }

    public Action parseMessage(String msg) {


        if (msg.startsWith(Message.PLAYER_MSG_PREFIX)) {
            msg = msg.split(Message.MSG_CONTENT_DELIMITER)[1];

            if (msg.equals(Message.FIND_MATCH_MSG)) {
                return new Action(Action.Type.FIND_MATCH);
            } else if (msg.equals(Message.CANCEL_MATCH_MSG)) {
                return new Action(Action.Type.CANCEL_MM);
            }
        } else if (msg.startsWith(Message.SERVER_INFO_MSG_PREFIX)) {
            return new Action(Action.Type.SERVER_INFO);
        } else if (msg.startsWith(Message.INTERNAL_SERVER_MSG_PREFIX)) {
            msg = msg.split(Message.MSG_CONTENT_DELIMITER)[1];

            if(msg.startsWith(Message.MATCH_FOUND_MSG)) {
                String[] content = msg.split("=")[1].split(",");
                int id = Integer.parseInt(content[0]);
                int color = Integer.parseInt(content[1]);

                return new Action(Action.Type.FOUND_MATCH, id, color);

            }
        }


        return null;
    }

}
