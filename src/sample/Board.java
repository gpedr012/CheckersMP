package sample;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;


public class Board extends GridPane
{
    private final int NUM_ROWS = 8, NUM_COLS = 8;

    private Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];


    public Board()
    {
        initTiles();

    }

    private void initTiles()
    {
        boolean currentColor = true;

        for (int i = 0; i < tiles.length; i++)
        {
            for (int j = 0; j < tiles[0].length ; j++)
            {
                tiles[i][j] = new Tile(currentColor);
                add(tiles[i][j], i, j);
                currentColor = !currentColor;

            }

            currentColor = !currentColor;
        }

    }



}
