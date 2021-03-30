package checkers.client.ui;

import checkers.client.game.Move;
import checkers.client.game.MoveList;
import javafx.animation.FadeTransition;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Iterator;

public class Piece extends StackPane {

    public enum PieceColor {

        LIGHT(Color.web("#ff0000"), Color.web("#c90000"), "Red"), DARK(Color.web("#2e2e2e"), Color.web("#141414"), "Black");

        private final Color innerColor;
        private final Color outerColor;
        private final String stringColor;

        PieceColor(Color outerColor, Color innerColor, String stringCol) {
            this.outerColor = outerColor;
            this.innerColor = innerColor;
            this.stringColor = stringCol;

        }

        public Color getOuterColor() {
            return outerColor;
        }

        public Color getInnerColor() {
            return innerColor;
        }


        @Override
        public String toString() {
            return stringColor;
        }
    }

    private final Circle outerCircle = new Circle(30);
    private final Circle innerCircle = new Circle(28);
    private final Circle selectionCircle = new Circle(outerCircle.getRadius());
    private final FadeTransition selectionAnimation = new FadeTransition(Duration.seconds(1.5), selectionCircle);
    private Image crownImage;
    private ImageView crownIView;
    private MoveList possibleMoves;
    private final PieceColor color;
    private int row = -1, col = -1;
    private boolean isEliminated = false;


    private boolean crowned = false;

    public Piece(PieceColor color) {
        initGraphics();
        initColor(color);
        initAnimation();
        this.color = color;
        possibleMoves = new MoveList();
        getChildren().addAll(selectionCircle, outerCircle, innerCircle);


    }

    public PieceColor getColor() {
        return color;
    }

    private void initGraphics() {
        innerCircle.setFill(null);
        innerCircle.setStrokeWidth(6);

        selectionCircle.setVisible(false);

    }

    private void initColor(PieceColor color) {
        outerCircle.setFill(color.getOuterColor());
        innerCircle.setStroke(color.getInnerColor());
        selectionCircle.setFill(null);
        selectionCircle.setStroke(Color.WHITE);
        selectionCircle.setStrokeWidth(10);
        selectionCircle.setCenterY(outerCircle.getCenterY());
        selectionCircle.setCenterX(outerCircle.getCenterX());


    }

    private void initAnimation() {
        selectionAnimation.setToValue(0.2);
        selectionAnimation.setAutoReverse(true);
        selectionAnimation.setCycleCount(Timeline.INDEFINITE);

    }


    public MoveList getPossibleMoves() {
        return possibleMoves;
    }

    public void setPossibleMoves(MoveList possibleMoves) {
        this.possibleMoves = possibleMoves;
    }

    public boolean hasAnyMoves() {

        return !possibleMoves.isEmpty();
    }

    public boolean isCrowned() {
        return crowned;
    }

    public void setEliminated(boolean value) {
        this.isEliminated = value;
    }

    public boolean isEliminated() {
        return isEliminated;
    }

    public void setCrowned(boolean val) {
        Image crownImage = new Image(getClass().getResourceAsStream("/crown.png"));
        ImageView crownIView = new ImageView(crownImage);
        crownIView.setFitWidth(35);
        crownIView.setFitHeight(35);

        if (color == PieceColor.DARK) {
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(1);
            crownIView.setEffect(colorAdjust);
        }

        this.crowned = val;
        getChildren().add(crownIView);
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setHighLight(boolean value, Color color) {

        System.out.println("Piece.setHighLight");
        selectionCircle.setOpacity(1);
        selectionCircle.setVisible(value);
        selectionCircle.setStroke(color);
        if (value) {

            selectionAnimation.playFromStart();
        } else {
            selectionAnimation.jumpTo(Duration.ZERO);
            selectionAnimation.stop();
        }
    }


    public int getCol() {
        return col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {

        return String.format("Piece@Row:%d/Col%d", row, col);
    }

}
