package checkers.ui;

import checkers.player.Player;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

//TODO: Setup changes depending on whether 1 player plays or two.
//If one player, always put player's pieces at the bottom of the board.
//Otherwise if 2 players, black pieces always on the bottom and white pieces on the top.

public class Board extends GridPane
{
    private final int NUM_ROWS = 8, NUM_COLS = 8, NUM_DARK_TILES = 32;

    private List<Tile> darkTiles = new ArrayList<>(NUM_DARK_TILES);


    public Board()
    {
        initTiles();
        initPieces(null, null);

    }

    private void initTiles()
    {
        //this boolean helps switch between the colors since they alternate between rows and cols.
        boolean colorAlternator = true;

        for (int i = 0; i < NUM_ROWS; i++)
        {
            for (int j = 0; j < NUM_COLS ; j++)
            {
                Tile tile = colorAlternator ? Tile.createLightTile() : Tile.createDarkTile();
                add(tile, j, i);
                if(!colorAlternator)
                {
                    darkTiles.add(tile);

                }

                colorAlternator = !colorAlternator;
            }

            colorAlternator = !colorAlternator;
        }
    }


    private void initPieces(Player playerOne, Player playerTwo)
    {
        //when it loops once change alignment so that the other pieces are placed by the bottom
        //and the two middle rows are skipped as is usual with checkers setup, so alignment becomes +8.
        int alignment = 0;
        boolean colorAlternator = true;


        //loop twice to place each of the 12 player's pieces.
        for (int i = 0; i < 2; i++)
        {
            for (int j = 0; j < 12; j++)
            {
                Piece piece = colorAlternator ? Piece.createLightPiece() : Piece.createDarkPiece();
                darkTiles.get(j + alignment).getChildren().add(piece);

            }
            alignment += 4 * 5; //skip 5 rows (each has 4 dark tiles).
            colorAlternator =  !colorAlternator;

        }

//        int count = 0;
//        for (components.Tile tile: darkTiles
//             )
//        {
//            tile.getChildren().add(new Label(Integer.toString(count)));
//            count++;
//        }


    }
}
