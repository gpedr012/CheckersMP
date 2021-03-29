package checkers.client.game;

import checkers.client.ui.Tile;

public class Move {
    private MoveType type;
    private Tile opponentTile;
    private Tile movementTile;

    public Move(Tile movementTile) {
        this.movementTile = movementTile;

    }

    public Tile getOpponentTile() {
        return opponentTile;
    }

    public Tile getMovementTile() {
        return movementTile;
    }

    public void setMovementTile(Tile movementTile) {
        this.movementTile = movementTile;
    }

    public void setOpponentTile(Tile opponentTile) {
        this.opponentTile = opponentTile;
    }

    public MoveType getPriorityType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

}
