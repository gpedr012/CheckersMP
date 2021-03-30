package checkers.client.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

public class ColorToggleMenu extends HBox {
    ColorToggle darkToggle;
    ColorToggle lightToggle;

    public ColorToggleMenu() {
        ColorToggle darkToggle = new ColorToggle(Piece.PieceColor.DARK);
        ColorToggle lightToggle = new ColorToggle(Piece.PieceColor.LIGHT);

        darkToggle.setOtherToggle(lightToggle);
        lightToggle.setOtherToggle(darkToggle);

        setAlignment(Pos.CENTER);
        setSpacing(10);

        this.darkToggle = darkToggle;
        this.lightToggle = lightToggle;

        getChildren().addAll(darkToggle, lightToggle);
    }

    public Piece.PieceColor getSelection() {
        if (darkToggle.isSelected.get()) {
            return Piece.PieceColor.DARK;
        } else if (lightToggle.isSelected.get()) {
            return Piece.PieceColor.LIGHT;
        } else {
            return Piece.PieceColor.DARK;
        }
    }
}
