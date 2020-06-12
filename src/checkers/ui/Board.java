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
    private final int NUM_ROWS = 8, NUM_COLS = 8;

    private List<List<Tile>> tiles = new ArrayList<List<Tile>>(NUM_ROWS * NUM_COLS);


    public Board(Player playerOne, Player playerTwo)
    {
        initTiles();
        addPiecesToPlayer(playerOne);
        addPiecesToPlayer(playerTwo);
    }



    private void initTiles()
    {
        boolean colorAlternator = true;
        boolean edgeAlternator = false;
        int darkTileCounter = -1;

        for (int i = 0; i < NUM_ROWS; i++)
        {
            tiles.add(new ArrayList<>(NUM_COLS));

            for (int j = 0; j < NUM_COLS ; j++)
            {
                Tile tile = colorAlternator ? Tile.createLightTile() : Tile.createDarkTile();
                add(tile, j, i);
                tiles.get(i).add(tile);
                colorAlternator = !colorAlternator;
            }

            colorAlternator = !colorAlternator;
        }
    }



    public void addPiecesToPlayer(Player player)
    {
        int startingRow = player.getPlayerNumber() == 1 ? NUM_ROWS - 3 : 0;

        for (int i = startingRow; i < startingRow + 3; i++)
        {
            for (int j = 0; j < tiles.get(i).size(); j++)
            {
                if(tiles.get(i).get(j).getColor() == Tile.DARK_COLOR)
                {
                    Piece piece = new Piece(player.getPlayerColor(), i, j);
                    player.addPiece(piece);
                    tiles.get(i).get(j).addPiece(piece);
                }

            }
        }
    }

    public List<List<Tile>> getTiles()
    {
        return tiles;
    }

    public Tile getTile(int row, int col)
    {
        return tiles.get(row).get(col);
    }
}
