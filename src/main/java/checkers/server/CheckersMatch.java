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

        int rowOrigin = args[1];
        int colOrigin = args[2];
        int rowDest = args[3];
        int colDest = args[4];

        StringBuffer msg = new StringBuffer();
        msg.append(Message.createMatchMoveMsg(-1, rowOrigin, colOrigin, rowDest, colDest));

        if(type == Action.Type.MOVE_ELIM) {
            int enemyRow = args[5];
            int enemyCol = args[6];

            msg.append(String.format("-%d-%d", enemyRow, enemyCol));


        }

        if(initiator == playerOne) {
            playerTwo.writeAndFlush(msg).sync();

        } else {
            playerOne.writeAndFlush(msg).sync();
        }
    }

    //Inverts the perspective since player pieces are always at the bottom thus row & col must be converted
    //for the enemy player before sending them.
    private void convertRowAndCol(int[] args) {
        final int MAX = 7; // max row & max col = 7.

        for (int i = 1; i <= args.length; i++) {
            args[i] = MAX - args[i];
        }


    }
}
