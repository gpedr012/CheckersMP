package checkers.network;

public class NetworkHelper {


    public final static String HOST = "127.0.0.1";
    public final static int PORT = 8095;

    private NetworkHelper(){}

    public static String getHost() {
        return HOST;
    }

    public static int getPort() {
        return PORT;
    }
}
