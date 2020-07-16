package checkers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class MenuController
{
    @FXML
    public Button localBtn;
    @FXML
    public Button onlineBtn;
    @FXML
    public Button quitBtn;
    @FXML
    public BorderPane mainPane;

    public void handleLocalBtn()
    {
        Scene scene = CheckersUtil.requestScene(this, "/checkers/mainmenulocal.fxml");
        Stage stage = (Stage)mainPane.getScene().getWindow();
        if (scene != null)
        {
            stage.setScene(scene);

        }

        else
        {
            System.out.println("Could not handle since menu was null.");
        }

    }

    public void handleOnlineBtn()
    {

    }

    public void handleQuitBtn()
    {
        Platform.exit();

    }




}
