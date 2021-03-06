package checkers.server;

import checkers.networkutils.Action;
import checkers.networkutils.Message;
import checkers.networkutils.Parser;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.css.Match;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class CheckersServerHandler extends SimpleChannelInboundHandler<String> {

    private Parser parser = new Parser();
    private Queue<Channel> channelQueue = new PriorityQueue<>();
    private List<Match> matchList = new ArrayList<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[SERVER SOUT]: Client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("[SERVER SOUT]: Received the following message = " + s);

        Action action = parser.parseMessage(s);

        switch (action) {
            case FIND_MATCH:

                if(channelQueue.isEmpty()) {
                    channelQueue.add(channelHandlerContext.channel());
                    channelHandlerContext.writeAndFlush(Message.createServerInfoMsg("You have been added to the queue"));

                } else if (channelQueue.contains(channelHandlerContext.channel())) {
                    channelHandlerContext.writeAndFlush(Message.createServerInfoMsg("You are already in the queue"));

                } else {

                }

                break;
        }


    }
}
