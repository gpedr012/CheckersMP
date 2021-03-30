package checkers.server;

import checkers.networkutils.Action;
import checkers.networkutils.Message;
import io.netty.channel.Channel;

public class CheckersMatch {

    private final Channel playerOne;
    private final Channel playerTwo;
    private final int playerOnePieceCtr;
    private final int playerTwoPieceCtr;

    public CheckersMatch(Channel playerOne, Channel playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        playerOnePieceCtr = playerTwoPieceCtr = 12;
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
        String msg;


        if (type == Action.Type.MOVE_ELIM) {

//            if(initiator == playerOne) {
//                playerTwoPieceCtr--;
//                if(playerTwoPieceCtr == 0) {
//                    playerOne.writeAndFlush();
//                    playerTwo.writeAndFlush();
//                    return;
//                }
//
//            } else {
//                playerOnePieceCtr--;
//                if(playerOnePieceCtr == 0) {
//                    playerOne.writeAndFlush();
//                    playerTwo.writeAndFlush();
//                    return;
//                }
//            }

            int enemyRow = args[Message.ROW_ELIM_IDX];
            int enemyCol = args[Message.COL_ELIM_IDX];

            msg = Message.createMatchMoveMsg(0, rowOrigin, colOrigin, rowDest, colDest, enemyRow, enemyCol);


        } else {
            msg = Message.createMatchMoveMsg(0, rowOrigin, colOrigin, rowDest, colDest);
        }

        if (initiator == playerOne) {
            playerTwo.writeAndFlush(msg).sync();

        } else {
            playerOne.writeAndFlush(msg).sync();
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
