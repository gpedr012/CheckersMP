package checkers.player;

import checkers.ui.Board;
import checkers.ui.Piece;
import checkers.ui.Tile;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.util.Iterator;


public class HumanPlayer extends Player
{
    private int playerNumber;
    private Piece.PieceColor playerColor;
    private Board board;
    private final Logic logic = new Logic();

    public HumanPlayer(Piece.PieceColor playerColor, int playerNumber, Board board)
    {
        super(playerColor, playerNumber, board);
        this.board = board;
        initLogic();

    }

//    public void test()
//    {
//        Tile curTile = board.getTile(2,1);
//        Tile newTile = board.getTile(3, 2);
//
//        double modifier = -40;
//
//        Bounds curB = curTile.localToScene(curTile.getBoundsInLocal());
//        Bounds newB = newTile.localToScene(newTile.getBoundsInLocal());
//
//        System.out.println("curB.getCenterX() = " + curB.getCenterX());
//        System.out.println("newB.getCenterX() = " + newB.getCenterX());
//        System.out.println("curB.getCenterY() = " + curB.getCenterY());
//        System.out.println("newB.getCenterY() = " + newB.getCenterY());
//
//        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1.5), curTile.getPiece());
//        translateTransition.setToX(newB.getCenterX() + modifier);
//        translateTransition.setFromX(curB.getCenterX() + modifier);
//        translateTransition.setToY(newB.getCenterY() + modifier);
//        translateTransition.setFromY(curB.getCenterY() + modifier);
//        translateTransition.setAutoReverse(true);
//        Piece piece = curTile.getPiece();
//        curTile.removePiece();
//        translateTransition.setCycleCount(Timeline.INDEFINITE);
//        board.getChildren().add(piece);
//        translateTransition.play();
//
//    }

    @Override
    public void processTurn()
    {
        calculatePossibleMoves();
        setHasTurn(true);
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
                    Tile currentTile = board.getTile(currentPiece.getRow(), currentPiece.getCol());
                    Tile newTile = (Tile)mouseEvent.getSource();
                    playMovementAnimation(currentTile, newTile);
                    newTile.highlightTile(false);
                    currentPiece.setRow(newTile.getRow());
                    currentPiece.setCol(newTile.getCol());
                    endTurn();

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
                    Iterator<Tile> iterator = clickedPiece.getPossibleMoves().iterator();
                    System.out.println("clickedPiece.getPossibleMoves().getPriority() = " + clickedPiece.getPossibleMoves().getPriority());
                    while(iterator.hasNext())
                    {
                        System.out.println("iterator.next().toString() = " + iterator.next().toString());
                    }



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
