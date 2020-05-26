package ui;

import javafx.scene.layout.GridPane;


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
                Tile tile = new Tile(currentColor);
                tiles[i][j] = tile;
                add(tile, i, j);
                currentColor = !currentColor;

            }

            currentColor = !currentColor;
        }

    }



}
