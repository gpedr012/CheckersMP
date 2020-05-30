package checkers.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Stack;

public class Piece extends StackPane
{
    private Circle outerCircle = new Circle(30);
    private Circle innerCircle = new Circle (28);
    private Image crownImage = new Image(getClass().getResourceAsStream("/crown.png"));
    private ImageView crownIView = new ImageView(crownImage);
    private List<Integer> possibleMoves = new Stack<>();

    private boolean crowned = false;

    private static final Color DARK_OUTER = Color.web("#2e2e2e");
    private static final Color DARK_INNER = Color.web("#141414");

    private static final Color LIGHT_OUTER = Color.web("#ff0000");
    private static final Color LIGHT_INNER = Color.web("#c90000");



    public Piece(Color colorOuter, Color colorInner)
    {
        initGraphics();
        initColor(colorOuter, colorInner);
        getChildren().addAll(outerCircle, innerCircle);

    }

    public static Piece createLightPiece() { return new Piece(LIGHT_OUTER, LIGHT_INNER); }
    public static Piece createDarkPiece() { return new Piece(DARK_OUTER, DARK_INNER); }

    private void initGraphics()
    {
        crownIView.setFitWidth(35);
        crownIView.setFitHeight(35);

        innerCircle.setFill(null);
        innerCircle.setStrokeWidth(6);
    }

    private void initColor(Color colorOuter, Color colorInner)
    {
        //true = light piece, false = dark piece.

        outerCircle.setFill(colorOuter);
        innerCircle.setStroke(colorInner);



    }

    public boolean isCrowned()
    {
        return crowned;
    }

    public void setCrowned(boolean val)
    {
        this.crowned = val;
        getChildren().add(crownIView);
    }



}
