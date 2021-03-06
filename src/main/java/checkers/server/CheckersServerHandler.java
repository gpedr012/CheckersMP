package checkers.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.PriorityQueue;
import java.util.Queue;

public class CheckersServerHandler extends SimpleChannelInboundHandler<String> {

    Queue<Channel> channelQueue = new PriorityQueue<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("[SERVER SOUT]: Client connected: " + ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("[SERVER SOUT]: Received the following message = " + s);


    }
}
