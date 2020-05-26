package ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece extends StackPane
{
    private Circle outerCircle = new Circle(30);
    private Circle innerCircle = new Circle (28);
    private Image crownImage = new Image(getClass().getResourceAsStream("/ui/crown.png"));
    private ImageView crownIView = new ImageView(crownImage);

    private boolean crowned = false;

    private static final Color COLOR_ONE_OUTER = Color.web("#2e2e2e");
    private static final Color COLOR_ONE_INNER = Color.web("#141414");

    private static final Color COLOR_TWO_OUTER = Color.web("#ff0000");
    private static final Color COLOR_TWO_INNER = Color.web("#c90000");





    public Piece(boolean color)
    {

        crownIView.setFitWidth(35);
        crownIView.setFitHeight(35);

        innerCircle.setFill(null);
        innerCircle.setStrokeWidth(6);


        //true = light piece, false = dark piece.
        if(color)
        {
            outerCircle.setFill(COLOR_TWO_OUTER);
            innerCircle.setStroke(COLOR_TWO_INNER);

        }
        else
        {
            outerCircle.setFill(COLOR_ONE_OUTER);
            innerCircle.setStroke(COLOR_ONE_INNER);
        }

        getChildren().addAll(outerCircle, innerCircle);

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
