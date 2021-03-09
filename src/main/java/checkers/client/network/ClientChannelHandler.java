package checkers.client.network;

import checkers.client.scenes.MainMenu;
import checkers.networkutils.Action;
import checkers.networkutils.Parser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class ClientChannelHandler extends SimpleChannelInboundHandler<String> {


    private Parser parser = new Parser();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        Action action = parser.parseMessage(msg);

        switch (action) {
            case SERVER_INFO:
                Platform.runLater(() -> MainMenu.showServerMsg(msg));
                break;
            case FIND_MATCH:
                Platform.runLater(() -> MainMenu.setIsFindingMatch(true));
                break;
            case CANCEL_MM:
                Platform.runLater(() -> MainMenu.setIsFindingMatch(false));
                break;
        }
    }


}
