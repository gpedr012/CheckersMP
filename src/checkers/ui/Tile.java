package checkers.ui;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Tile extends StackPane
{
    public static final int SIDE_LENGTH = 80;
    private final int highlightModifier = 5;

    private Rectangle tileBackground = new Rectangle(SIDE_LENGTH, SIDE_LENGTH);
    private Rectangle highLight = new Rectangle(SIDE_LENGTH - highlightModifier, SIDE_LENGTH - highlightModifier);
    private boolean containsPiece = false;
    private Piece piece = null;
    private boolean isEdge;
    private final Color color;
    private FadeTransition highLightAnimation;


    public static final Color LIGHT_COLOR = Color.SANDYBROWN;
    public static final Color DARK_COLOR = Color.SADDLEBROWN;


    public Tile(Color color)
    {
        //true = light tile, false = dark tile.
        this.color = color;
        tileBackground.setFill(color);
        initHighLight();
        initAnim();

        getChildren().addAll(tileBackground, highLight);


    }

    private void initHighLight()
    {
        highLight.setFill(null);
        highLight.setStroke(Color.GOLD);
        highLight.setStrokeWidth(highlightModifier);
        highLight.setVisible(false);

    }

    private void initAnim()
    {
        highLightAnimation = new FadeTransition(Duration.seconds(1.5), highLight);
        highLightAnimation.setToValue(0.2);
        highLightAnimation.setCycleCount(Timeline.INDEFINITE);
        highLightAnimation.setAutoReverse(true);


    }


    public static Tile createLightTile()
    {
        return new Tile(LIGHT_COLOR);
    }

    public static Tile createDarkTile()
    {
        return new Tile(DARK_COLOR);
    }

    public boolean containsPiece()
    {
        return containsPiece;

    }

    public Color getColor()
    {
        return color;
    }

    public Piece getPiece()
    {
        return piece;
    }

    public void addPiece(Piece piece)
    {
        getChildren().add(getChildren().size() - 1, piece);
        this.piece = piece;
        containsPiece = true;

    }

    public void removePiece()
    {
        getChildren().remove(piece);
        containsPiece = false;
        piece = null;

    }

    public boolean isEdge()
    {
        return isEdge;

    }

    public void setEdge(boolean edge)
    {
        isEdge = edge;

    }

    public void highlightTile(boolean value)
    {
        highLight.setVisible(value);
        if(value)
        {
            highLightAnimation.play();
        }
        else
        {
            highLightAnimation.jumpTo(Duration.ZERO);
            highLightAnimation.stop();
        }

    }

}
