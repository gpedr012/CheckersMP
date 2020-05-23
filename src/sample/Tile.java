package sample;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile extends StackPane
{
    private final int sideLength = 40;

    private Rectangle tileBackground = new Rectangle(sideLength, sideLength);
    private boolean containsPiece = false;


    public Tile(boolean colorBool)
    {
        //true = white tile, false = black tile.
        System.out.println("colorBool = " + colorBool);
        Color color = colorBool ? Color.WHITE : Color.BLACK;
        tileBackground.setFill(color);
        getChildren().add(tileBackground);

    }

    public boolean containsPiece()
    {
        return containsPiece;
    }

    public void setContainsPiece(boolean containsPiece)
    {
        this.containsPiece = containsPiece;
    }
}
