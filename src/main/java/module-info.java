module CheckersMP {
    requires javafx.fxml;
    requires javafx.controls;

    opens checkers.player;
    opens checkers.ui;
    opens checkers;

}