package checkers.network;

import io.netty.channel.Channel;

public class NetworkHelper {


    public final static String HOST = "127.0.0.1";
    public final static int PORT = 8095;
    private static Channel userChannel = null;

    private NetworkHelper(){}

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
        NetworkHelper.userChannel = userChannel;
    }
}
