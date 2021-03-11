package checkers.client.network;

import checkers.client.scenes.MainMenu;
import checkers.client.ui.Piece;
import checkers.networkutils.Action;
import checkers.networkutils.Parser;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class ClientChannelHandler extends SimpleChannelInboundHandler<String> {


    private Parser parser = new Parser();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {

        System.out.println("DEBUG]>RECEIVED MSG--> : " + msg);

        Action action = parser.parseMessage(msg);

        switch (action.getType()) {
            case SERVER_INFO:
                Platform.runLater(() -> MainMenu.showServerMsg(msg));
                break;
            case FIND_MATCH:
                Platform.runLater(() -> MainMenu.setIsFindingMatch(true));
                break;
            case CANCEL_MM:
                Platform.runLater(() -> MainMenu.setIsFindingMatch(false));
                break;
            case FOUND_MATCH:
                Piece.PieceColor playerColor;

                switch(action.getArg(1)) {
                    case 0:
                        playerColor = Piece.PieceColor.DARK;
                        break;
                    case 1:
                        playerColor = Piece.PieceColor.LIGHT;
                        break;
                    default:
                        throw new Exception("Unrecognized color ID");
                }
                Platform.runLater(() -> MainMenu.startOnlineGame(action.getArg(0), playerColor));
                break;

            default:
                throw new Exception("Unrecognized action");
        }
    }


}
