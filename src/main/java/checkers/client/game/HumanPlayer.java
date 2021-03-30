package checkers.client.game;

import checkers.client.network.ClientNetworkHelper;
import checkers.client.ui.Board;
import checkers.client.ui.Piece;
import checkers.client.ui.Tile;
import checkers.networkutils.Message;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Iterator;


public class HumanPlayer extends Player {
    private final Logic logic = new Logic();

    public HumanPlayer(Piece.PieceColor playerColor, int playerNumber, Board board) {
        super(playerColor, playerNumber, board);
        initLogic();

    }


    @Override
    public void processTurn() {
        calculatePossibleMoves();
        for (Piece piece : getPiecesWithMoves()) {
            piece.setHighLight(true, Color.WHITE);

        }

    }

    @Override
    public void endTurn() {
        setHasTurn(false);
        for (Piece piece : getPiecesWithMoves()) {
            piece.setHighLight(false, null);

        }
        setHighLightTiles(false, selectedPiece.get());

        if (ClientNetworkHelper.isInOnlineGame()) {
            ClientNetworkHelper.flushBufferToServer();

        }

    }

    private void initLogic() {
        selectedPiece.addListener(new ChangeListener<Piece>() {
            @Override
            public void changed(ObservableValue<? extends Piece> observableValue, Piece oldPiece, Piece newPiece) {
                if (oldPiece != null) {
                    if (oldPiece.hasAnyMoves()) {

                        oldPiece.setHighLight(true, Color.WHITE);
                        setHighLightTiles(false, oldPiece);
                    } else {
                        oldPiece.setHighLight(false, null);
                    }
                }

            }
        });

        for (Piece piece : piecesList) {
            piece.onMouseClickedProperty().bind(new ObjectBinding<EventHandler<? super MouseEvent>>() {
                {
                    super.bind(hasTurnProperty());
                }

                @Override
                protected EventHandler<? super MouseEvent> computeValue() {
                    if (hasTurnProperty().get()) {

                        return logic.getPieceLogic();
                    } else {
                        return null;
                    }
                }
            });
        }

    }

    private void setHighLightTiles(boolean value, Piece piece) {

        Iterator<Move> iterator = piece.getPossibleMoves().iterator();
        while (iterator.hasNext()) {
            Move move = iterator.next();
            move.getMovementTile().highlightTile(value);
            if (value) {
                move.getMovementTile().setOnMouseClicked(logic.getTileLogic());
            } else {
                move.getMovementTile().setOnMouseClicked(null);
            }

        }

    }

    private class Logic {
        EventHandler<MouseEvent> tileLogic;
        EventHandler<MouseEvent> pieceLogic;

        public Logic() {

            tileLogic = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    Piece currentPiece = selectedPiece.get();
                    Tile currentTile = getBoard().getTile(currentPiece.getRow(), currentPiece.getCol());
                    Tile destinationTile = (Tile) mouseEvent.getSource();


                    if (ClientNetworkHelper.isInOnlineGame()) {
                        int matchId = ClientNetworkHelper.getMatchId();
                        int rowOrigin = currentTile.getRow();
                        int colOrigin = currentTile.getCol();
                        int rowDest = destinationTile.getRow();
                        int colDest = destinationTile.getCol();

                        if (currentPiece.getPossibleMoves().getPriority() == MoveType.REQUIRED) {
                            Move currentMove = currentPiece.getPossibleMoves().findMove(destinationTile);
                            Tile enemyTile = currentMove.getOpponentTile();
                            int enemyRow = enemyTile.getRow();
                            int enemyCol = enemyTile.getCol();

                            ClientNetworkHelper.addToBuffer(Message.createMatchMoveMsg(matchId, rowOrigin, colOrigin, rowDest, colDest, enemyRow, enemyCol));

                        } else {
                            ClientNetworkHelper.addToBuffer(Message.createMatchMoveMsg(matchId, rowOrigin, colOrigin, rowDest, colDest));

                        }


                    }

                    playMovementAnimation(currentTile, destinationTile);

                    destinationTile.highlightTile(false);

                }
            };

            pieceLogic = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    Piece clickedPiece = (Piece) mouseEvent.getSource();
                    selectedPiece.setValue(clickedPiece);
                    clickedPiece.setHighLight(true, Color.GOLD);
                    setHighLightTiles(true, clickedPiece);


                }
            };
        }

        public EventHandler<MouseEvent> getTileLogic() {
            return tileLogic;
        }

        public EventHandler<MouseEvent> getPieceLogic() {

            return pieceLogic;
        }
    }
}
