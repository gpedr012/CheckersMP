package checkers.client.game;

import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Iterator;


public class HumanPlayer extends Player
{
    private final Logic logic = new Logic();
    private boolean isOnline = false;
    private int matchId = 0;

    public HumanPlayer(Piece.PieceColor playerColor, int playerNumber, Board board)
    {
        super(playerColor, playerNumber, board);
        initLogic();

    }

    public HumanPlayer(Piece.PieceColor playerColor, int playerNumber, Board board, int matchId)
    {
        super(playerColor, playerNumber, board);
        this.matchId = matchId;
        isOnline = true;
        initLogic();

    }

    @Override
    public void processTurn()
    {
        calculatePossibleMoves();
        for (Piece piece : getPiecesWithMoves())
        {
            piece.setHighLight(true, Color.WHITE);

        }

    }

    @Override
    public void endTurn()
    {
        setHasTurn(false);
        for (Piece piece : getPiecesWithMoves())
        {
            piece.setHighLight(false, null);

        }
        setHighLightTiles(false, selectedPiece.get());

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
                        setHighLightTiles(false, oldPiece);
                    }
                    else
                    {
                        oldPiece.setHighLight(false, null);
                    }
                }

            }
        });

        for (Piece piece : piecesList)
        {
            piece.onMouseClickedProperty().bind(new ObjectBinding<EventHandler<? super MouseEvent>>()
            {
                {
                    super.bind(hasTurnProperty());
                }

                @Override
                protected EventHandler<? super MouseEvent> computeValue()
                {
                    if(hasTurnProperty().get())
                    {
                        return logic.getPieceLogic();
                    }
                    else
                    {
                        return null;
                    }
                }
            });
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
                    Tile currentTile = getBoard().getTile(currentPiece.getRow(), currentPiece.getCol());
                    Tile newTile = (Tile)mouseEvent.getSource();

                    playMovementAnimation(currentTile, newTile);

                    newTile.highlightTile(false);

                    if(isOnline) {

                    }
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
