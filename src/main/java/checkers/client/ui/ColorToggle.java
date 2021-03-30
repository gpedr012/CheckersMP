package checkers.client.ui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ColorToggle extends StackPane {
    Piece pieceBg;
    Rectangle bg = new Rectangle(60, 60);
    BooleanProperty isSelected = new SimpleBooleanProperty(false);

    public ColorToggle(Piece.PieceColor color) {


        bg.setFill(Color.DARKGRAY);
        bg.setStrokeWidth(3);
        bg.setArcHeight(20);
        bg.setArcWidth(20);
        pieceBg = new Piece(color);
        pieceBg.setScaleX(0.75);
        pieceBg.setScaleY(0.75);
        getChildren().add(bg);
        getChildren().add(pieceBg);
        setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!isSelected.get()) {
                    isSelected.set(true);
                    System.out.println("isSelected.get() = " + isSelected.get());

                }
            }
        });

        isSelected.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldV, Boolean newV) {
                if (newV) {
                    pieceBg.setOpacity(100);
                    bg.setStroke(Color.WHITE);

                } else {
                    pieceBg.setOpacity(50);
                    bg.setStroke(Color.DARKGRAY);
                }
            }
        });

        setId("sethover");
    }

    public void setOtherToggle(ColorToggle otherToggle) {
        isSelectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldV, Boolean newV) {
                otherToggle.isSelectedProperty().set(!newV);

            }
        });

    }

    public BooleanProperty isSelectedProperty() {
        return isSelected;
    }
}
