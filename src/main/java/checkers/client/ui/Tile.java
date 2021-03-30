package checkers.client.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Tile extends StackPane {
    public static final int SIDE_LENGTH = 80;
    private final int highlightModifier = 5;

    private final Rectangle tileBackground = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
    private final Rectangle highLight = new Rectangle(SIDE_LENGTH - highlightModifier, SIDE_LENGTH - highlightModifier);
    private boolean containsPiece = false;
    private Piece piece = null;
    private boolean isEdge;
    private final Color color;
    private FadeTransition highLightAnimation;
    private final int row, col;


    public static final Color LIGHT_COLOR = Color.SANDYBROWN;
    public static final Color DARK_COLOR = Color.SADDLEBROWN;


    public Tile(Color color, int row, int col) {
        //true = light tile, false = dark tile.
        this.color = color;
        this.row = row;
        this.col = col;
        tileBackground.setFill(color);
        initHighLight();
        initAnim();

//        Label debugLabel = new Label(String.format("Row:%d Col:%d", row, col));
//
//        getChildren().addAll(tileBackground, highLight, debugLabel);

        getChildren().addAll(tileBackground, highLight);
    }

    private void initHighLight() {
        highLight.setFill(null);
        highLight.setStroke(Color.GOLD);
        highLight.setStrokeWidth(highlightModifier);
        highLight.setVisible(false);

    }

    private void initAnim() {
        highLightAnimation = new FadeTransition(Duration.seconds(1.5), highLight);
        highLightAnimation.setToValue(0.2);
        highLightAnimation.setCycleCount(Timeline.INDEFINITE);
        highLightAnimation.setAutoReverse(true);


    }


    public static Tile createLightTile(int row, int col) {
        return new Tile(LIGHT_COLOR, row, col);
    }

    public static Tile createDarkTile(int row, int col) {
        return new Tile(DARK_COLOR, row, col);
    }

    public boolean containsPiece() {
        return containsPiece;

    }

    public Color getColor() {
        return color;
    }

    public Piece getPiece() {
        return piece;
    }

    public void addPiece(Piece piece) {
        getChildren().add(getChildren().size() - 1, piece);
        this.piece = piece;
        containsPiece = true;

    }

    public void removePiece() {
        getChildren().remove(piece);
        containsPiece = false;
        piece = null;

    }

    public boolean isEdge() {
        return isEdge;

    }

    public void eliminatePiece() {
        containsPiece = false;
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.25), piece);
        fadeTransition.setToValue(0);
        fadeTransition.play();
        piece.setEliminated(true);
        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                removePiece();
            }
        });

    }

    public void setEdge(boolean edge) {
        isEdge = edge;

    }

    public void highlightTile(boolean value) {
        highLight.setVisible(value);
        if (value) {
            highLightAnimation.play();
        } else {
            highLightAnimation.jumpTo(Duration.ZERO);
            highLightAnimation.stop();
        }

    }

    //TODO: DEBUG<REMOVE LATER.
    public void highlightTile(boolean value, Color color) {
        highLight.setVisible(value);
        if (value) {
            highLightAnimation.play();
        } else {
            highLightAnimation.jumpTo(Duration.ZERO);
            highLightAnimation.stop();
        }
        highLight.setStroke(color);

    }

    @Override
    public String toString() {
        return String.format("Tile@Row=%dCol=%d\n", row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
