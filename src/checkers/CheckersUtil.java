package checkers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class CheckersUtil
{

    public static <T> Scene requestScene(T requestor, String filename)
    {
        try
        {
            Parent root = FXMLLoader.load(requestor.getClass().getResource(filename));
            return new Scene(root, 500, 500);


        } catch (IOException e)
        {
            System.out.println("ERROR IN GETLOCALMENU");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return null;
        }


    }
}
