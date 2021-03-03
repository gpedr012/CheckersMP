module CheckersMP {
    requires javafx.fxml;
    requires javafx.controls;
    requires io.netty.all;

    opens checkers.ui;
    opens checkers;

}