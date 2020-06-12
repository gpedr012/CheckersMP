package checkers.ui;

import checkers.player.MoveList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;
import java.util.Stack;

public class Piece extends StackPane
{

    public enum PieceColor
    {

        LIGHT(Color.web("#ff0000"), Color.web("#c90000")), DARK(Color.web("#2e2e2e"), Color.web("#141414"));

        private Color innerColor, outerColor;
        PieceColor(Color outerColor, Color innerColor)
        {
            this.outerColor = outerColor;
            this.innerColor = innerColor;

        }

        public Color getOuterColor()
        {
            return outerColor;
        }

        public Color getInnerColor()
        {
            return innerColor;
        }


    }

    private Circle outerCircle = new Circle(30);
    private Circle innerCircle = new Circle (28);
    private Image crownImage = new Image(getClass().getResourceAsStream("/crown.png"));
    private ImageView crownIView = new ImageView(crownImage);
    private MoveList possibleMoves;
    private PieceColor color;
    private int row = -1, col = -1;


    private boolean crowned = false;

    public Piece(PieceColor color, int row, int col)
    {
        initGraphics();
        initColor(color);
        this.color = color;
        getChildren().addAll(outerCircle, innerCircle);

    }

    public PieceColor getColor()
    {
        return color;
    }

    private void initGraphics()
    {
        crownIView.setFitWidth(35);
        crownIView.setFitHeight(35);

        innerCircle.setFill(null);
        innerCircle.setStrokeWidth(6);
    }

    private void initColor(PieceColor color)
    {
        outerCircle.setFill(color.getOuterColor());
        innerCircle.setStroke(color.getInnerColor());

    }

    public MoveList getPossibleMoves()
    {
        return possibleMoves;
    }

    public void setPossibleMoves(MoveList possibleMoves)
    {
        this.possibleMoves = possibleMoves;
    }

    public boolean hasAnyMoves()
    {

        return possibleMoves.isEmpty();
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

    public void setPosition(int row, int col)
    {
        this.row = row;
        this.col = col;
    }

    public int getRow()
    {
        return row;
    }

    public int getCol()
    {
        return col;
    }
}
