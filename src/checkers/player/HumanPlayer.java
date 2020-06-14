package checkers.player;

import checkers.ui.Board;
import checkers.ui.Piece;
import checkers.ui.Tile;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Iterator;


public class HumanPlayer extends Player
{
    private int playerNumber;
    private Piece.PieceColor playerColor;
    private Board board;
    private MoveCalculator moveCalculator;
    private ObjectProperty<Piece> selectedPiece = new SimpleObjectProperty<>();
    private final Logic logic = new Logic();


    public HumanPlayer(Piece.PieceColor playerColor, int playerNumber)
    {
        super(playerColor, playerNumber);
        initLogic();


    }

    @Override
    public void processTurn()
    {

    }

    private void initLogic()
    {
        selectedPiece.addListener(new ChangeListener<Piece>()
        {
            @Override
            public void changed(ObservableValue<? extends Piece> observableValue, Piece oldPiece, Piece newPiece)
            {
                if (oldPiece != null)
                {
                    if(oldPiece.hasAnyMoves())
                    {
                        oldPiece.setHighLight(true, Color.WHITE);
                    }
                    else
                    {
                        oldPiece.setHighLight(false, null);
                    }
                }

            }
        });

        for (Piece piece : getPiecesList())
        {
            if(piece.hasAnyMoves())
            {
                piece.setHighLight(true, Color.WHITE);
                System.out.println("HumanPlayer.initLogic");
            }
            piece.setOnMouseClicked(logic.getPieceLogic());

        }


    }

    private void setHighLightTiles(boolean value, Piece piece)
    {
        Iterator<Tile> iterator = piece.getPossibleMoves().iterator();
        while(iterator.hasNext())
        {
            Tile tile = iterator.next();
            tile.highlightTile(value);
            if(value)
            {
                tile.setOnMouseClicked(logic.getTileLogic());
            }
            else
            {
                tile.setOnMouseClicked(null);
            }

        }

    }

    public Piece getSelectedPiece()
    {
        return selectedPiece.get();
    }

    public ObjectProperty<Piece> selectedPieceProperty()
    {
        return selectedPiece;
    }

    public void setSelectedPiece(Piece selectedPiece)
    {
        this.selectedPiece.set(selectedPiece);
    }

    private class Logic
    {
        EventHandler<MouseEvent> tileLogic;
        EventHandler<MouseEvent> pieceLogic;

        public Logic()
        {
            tileLogic = new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    Piece currentPiece = selectedPiece.get();
                    Tile currentTile = board.getTile(currentPiece.getRow(), currentPiece.getCol());
                    Tile newTile = (Tile)mouseEvent.getSource();
                    currentTile.removePiece();
                    newTile.addPiece(currentPiece);


                }
            };

            pieceLogic = new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent mouseEvent)
                {
                    Piece clickedPiece = (Piece)mouseEvent.getSource();
                    selectedPiece.setValue(clickedPiece);
                    clickedPiece.setHighLight(true, Color.GOLD);
                    setHighLightTiles(true, clickedPiece);

                }
            };
        }

        public EventHandler<MouseEvent> getTileLogic()
        {
            return tileLogic;
        }

        public EventHandler<MouseEvent> getPieceLogic()
        {
            return pieceLogic;
        }
    }
}
