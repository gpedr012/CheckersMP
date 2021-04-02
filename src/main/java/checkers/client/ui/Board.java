package checkers.client.ui;

import checkers.client.game.Move;
import checkers.client.game.Player;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Board extends GridPane {
    private final int NUM_ROWS = 8, NUM_COLS = 8;

    private final List<List<Tile>> tiles = new ArrayList<List<Tile>>(NUM_ROWS * NUM_COLS);
    private Stack<Move> moveStack = new Stack<>();


    public Board() {
        initTiles();
    }

    public Board(Player playerOne, Player playerTwo) {
        initTiles();
        addPiecesToBoard(playerOne);
        addPiecesToBoard(playerTwo);
    }


    private void initTiles() {
        boolean colorAlternator = true;

        for (int i = 0; i < NUM_ROWS; i++) {
            tiles.add(new ArrayList<>(NUM_COLS));

            for (int j = 0; j < NUM_COLS; j++) {
                Tile tile = colorAlternator ? Tile.createLightTile(i, j) : Tile.createDarkTile(i, j);
                add(tile, j, i); //gridpane uses col, row as args. reason why it's reversed.
                tiles.get(i).add(tile);
                colorAlternator = !colorAlternator;
            }

            colorAlternator = !colorAlternator;
        }
    }


    public void addPiecesToBoard(Player player) {
        int startingRow = player.getPlayerNumber() == 1 ? NUM_ROWS - 3 : 0;
        int pieceCounter = 0;

        for (int i = startingRow; i < startingRow + 3; i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                if (tiles.get(i).get(j).getColor() == Tile.DARK_COLOR) {
                    Piece piece = player.getPiece(pieceCounter);
                    tiles.get(i).get(j).addPiece(piece);
                    piece.setRow(i);
                    piece.setCol(j);
                    pieceCounter++;

                }

            }
        }
    }

    @Override
    public String toString() {
        return Integer.toString(hashCode());
    }

    public void addMoveToStack(Move move) {
        moveStack.push(move);

    }

    public Move popMove() {
        return moveStack.pop();
    }

    public boolean isMoveStackEmpty() {
        return moveStack.isEmpty();
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

    public Tile getTile(int row, int col) {
        return tiles.get(row).get(col);
    }
}
