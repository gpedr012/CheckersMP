package checkers.ui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane
{
    private final int sideLength = 80;

    private Rectangle tileBackground = new Rectangle(sideLength, sideLength);
    private boolean containsPiece = false;
    private Piece piece = null;
    private boolean isEdge;
    private final Color color;



    public static final Color LIGHT_COLOR = Color.SANDYBROWN;
    public static final Color DARK_COLOR = Color.SADDLEBROWN;


    public Tile(Color color)
    {
        //true = light tile, false = dark tile.
        this.color = color;
        tileBackground.setFill(color);
        getChildren().add(tileBackground);

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
        getChildren().add(piece);
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

}
