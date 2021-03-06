package checkers.client.network;

import checkers.networkutils.Message;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

public class ClientNetworkHelper {


    public final static String HOST = "127.0.0.1";
    public final static int PORT = 8095;
    private static Channel userChannel = null;
    private static boolean isConnected = false;
    private static EventLoopGroup eventLoopGroup;

    private ClientNetworkHelper(){}

    public static String getHost() {

        return HOST;


    }

    public static int getPort() {
        return PORT;
    }

    public static Channel getUserChannel() {
        return userChannel;
    }

    public static void setUserChannel(Channel userChannel) {
        ClientNetworkHelper.userChannel = userChannel;
        isConnected = true;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public static EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public static void setEventLoopGroup(EventLoopGroup eventLoopGroup) {
        ClientNetworkHelper.eventLoopGroup = eventLoopGroup;
    }

    public static void findMatch() {

        sendMessage(Message.createFindMatchMsg());


    }

    private static void sendMessage(String s) {
        Task<Void> sendMsgTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                userChannel.writeAndFlush(s).sync();

                return null;
            }

            @Override
            protected void failed() {
                Alert alert = Message.createConnectionErrorAlert();
                alert.showAndWait();
            }
        };

        new Thread(sendMsgTask).start();
    }
}
