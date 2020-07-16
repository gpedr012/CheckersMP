package checkers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuLocalController
{
    @FXML
    BorderPane mainPane;
    @FXML
    Button singlePlayerBtn;
    @FXML
    Button twoPlayerBtn;
    @FXML
    Button goBackBtn;


    public void handleGoBackBtn()
    {
        Scene scene = CheckersUtil.requestScene(this, "/checkers/mainmenu.fxml");
        Stage stage = (Stage)mainPane.getScene().getWindow();
        if (scene != null)
        {
            stage.setScene(scene);
        }
        else
        {
            System.out.println("SCENE WAS NULL (HANDLEGOBACKBTN)");
        }

    }
}
