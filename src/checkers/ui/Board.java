package checkers.ui;

import checkers.player.Player;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//TODO: Setup changes depending on whether 1 player plays or two.
//If one player, always put player's pieces at the bottom of the board.
//Otherwise if 2 players, black pieces always on the bottom and white pieces on the top.

public class Board extends GridPane
{
    private final int NUM_ROWS = 8, NUM_COLS = 8, NUM_DARK_TILES = 32, NUM_DARK_TILES_PER_ROW = 4;

    private List<List<Tile>> darkTiles = new ArrayList<List<Tile>>(NUM_DARK_TILES);


    public Board(Player playerOne, Player playerTwo)
    {
        initTiles();
        initPieces(playerOne);
        initPieces(playerTwo);



    }


    private void initTiles()
    {
        //this boolean helps switch between the colors since they alternate between rows and cols.
        boolean colorAlternator = true;
        //The piece that is on the edge alternates from being first to last on each row.
        //true = 1st tile is edge, false = last tile is edge.
        boolean edgeAlternator = false;
        int darkTileCounter = -1;

        for (int i = 0; i < NUM_ROWS; i++)
        {
            darkTiles.add(new ArrayList<>(NUM_DARK_TILES_PER_ROW));

            for (int j = 0; j < NUM_COLS ; j++)
            {
                Tile tile = colorAlternator ? Tile.createLightTile() : Tile.createDarkTile();
                add(tile, j, i);
                if(!colorAlternator)
                {
                    darkTiles.get(i).add(tile);


                }

                colorAlternator = !colorAlternator;
            }

            if (edgeAlternator)
            {
                darkTiles.get(i).get(0).setEdge(true);
            } else
            {
                darkTiles.get(i).get(NUM_DARK_TILES_PER_ROW - 1).setEdge(true);
            }
            edgeAlternator = !edgeAlternator;
            colorAlternator = !colorAlternator;
        }
    }


    private void initPieces(Player player)
    {
        int startingRow = player.getPlayerNumber() == 1 ? NUM_ROWS - 3 : 0;

        for (int i = startingRow; i < startingRow + 3; i++)
        {
            for (int j = 0; j < darkTiles.get(i).size(); j++)
            {
                darkTiles.get(i).get(j).getChildren().add(new Piece(player.getPlayerColor(), i, j));
            }
        }


    }


}
