package checkers.server;

import checkers.networkutils.Action;
import checkers.networkutils.Message;
import checkers.networkutils.Parser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.ArrayList;
import java.util.List;


public class CheckersServerHandler extends SimpleChannelInboundHandler<String> {

    private Parser parser = new Parser();
    private static List<Channel> channelQueue = new ArrayList<>();
    private static List<CheckersMatch> matchList = new ArrayList<>();
    private static int matchIdCtr = 0;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[SERVER SOUT]: Client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("[SERVER SOUT]: Received the following message = " + s);

        Action action = parser.parseMessage(s);

        switch (action.getType()) {
            case FIND_MATCH:

                if(channelQueue.isEmpty()) {
                    channelQueue.add(channelHandlerContext.channel());
                    channelHandlerContext.writeAndFlush(Message.createFindMatchMsg() +  Message.createServerInfoMsg("You have been added to the queue")).sync();

                } else {
                    Channel opponentChannel = channelQueue.remove(0);
                    Channel senderChannel = channelHandlerContext.channel();

                    opponentChannel.writeAndFlush(Message.createMatchFoundMsg(matchIdCtr, 0)).sync();
                    senderChannel.writeAndFlush(Message.createMatchFoundMsg(matchIdCtr, 1)).sync();

                    matchList.add(new CheckersMatch(opponentChannel, senderChannel));
                    opponentChannel.writeAndFlush(Message.createHasTurnMsg()).sync();

                    matchIdCtr++;

                    //TODO: create internal match representation.
                }

                break;

            case CANCEL_MM:
                Channel channel = channelHandlerContext.channel();
                if(channelQueue.remove(channel)) {
                    channel.writeAndFlush(Message.createCancelMatchMakingMsg() + Message.createServerInfoMsg("You have been removed from the queue."));
                }

            case MOVE_ELIM:
            case MOVE_NO_ELIM:
                matchList.get(action.getArg(0)).advance(channelHandlerContext.channel(), action.getType(),action.getAllArgs());

                break;

            default:
                throw new Exception("Incorrect action found");
        }


    }
}
