package checkers.server;

import checkers.networkutils.Action;
import checkers.networkutils.Message;
import io.netty.channel.Channel;

public class CheckersMatch {

    private Channel playerOne;
    private Channel playerTwo;

    public CheckersMatch(Channel playerOne, Channel playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
    }

    public Channel getPlayerOne() {
        return playerOne;
    }

    public Channel getPlayerTwo() {
        return playerTwo;
    }

    public void advance(Channel initiator, Action.Type type, int[] args) throws InterruptedException {
        convertRowAndCol(args);

        int rowOrigin = args[Message.ROW_ORIGIN_IDX];
        int colOrigin = args[Message.COL_ORIGIN_IDX];
        int rowDest = args[Message.ROW_DEST_IDX];
        int colDest = args[Message.COL_DEST_IDX];

        StringBuffer msg = new StringBuffer();
        msg.append(Message.createMatchMoveMsg(0, rowOrigin, colOrigin, rowDest, colDest));

        if(type == Action.Type.MOVE_ELIM) {
            int enemyRow = args[Message.ROW_ELIM_IDX];
            int enemyCol = args[Message.COL_ELIM_IDX];

            msg.append(String.format("-%d-%d", enemyRow, enemyCol));


        }

        if(initiator == playerOne) {
            playerTwo.writeAndFlush(msg).sync();
            playerTwo.writeAndFlush(Message.createHasTurnMsg()).sync();

        } else {
            playerOne.writeAndFlush(msg).sync();
            playerOne.writeAndFlush(Message.createHasTurnMsg()).sync();
        }
    }

    //Inverts the perspective since player pieces are always at the bottom thus row & col must be converted
    //for the enemy player before sending them.
    private void convertRowAndCol(int[] args) {
        final int MAX = 7; // max row & max col = 7.

        for (int i = 1; i < args.length; i++) {
            args[i] = MAX - args[i];
        }


    }
}
