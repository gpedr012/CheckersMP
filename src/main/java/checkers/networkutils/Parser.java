package checkers.networkutils;

public class Parser {


    public Parser() {
    }

    public Action parseMessage(String msg) throws Exception {


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

            if (msg.startsWith(Message.MATCH_FOUND_MSG)) {
                String[] content = msg.split("=")[1].split(",");
                int[] parsedContent = parseContent(content);

                return new Action(Action.Type.FOUND_MATCH, parsedContent);

            } else if (msg.startsWith(Message.HAS_TURN_MSG)) {
                return new Action(Action.Type.HAS_TURN);

            } else if (msg.startsWith(Message.MATCH_MOVE_MSG)) {

                String[] content = msg.split("=")[1].split("-");
                int[] parsedContent = parseContent(content);

                if(parsedContent.length == 5) {
                    return new Action(Action.Type.MOVE_NO_ELIM, parsedContent);


                } else if (parsedContent.length == 7) {
                    return new Action(Action.Type.MOVE_ELIM, parsedContent);

                } else {
                    throw new Exception("Incorrect args");

                }

            }
        }


        return null;
    }

    private int[] parseContent(String[] content) {
        int[] parsedContent = new int[content.length];

        for (int i = 0; i < content.length; i++) {
            parsedContent[i] = Integer.parseInt(content[i]);
        }

        return parsedContent;
    }

}
