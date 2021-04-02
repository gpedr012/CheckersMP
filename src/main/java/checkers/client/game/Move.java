package checkers.client.game;

import checkers.client.ui.Tile;

public class Move {
    private MoveType type;
    private Tile opponentTile;
    private Tile movementTile;
    private Tile originalTile;

    public Move(Tile originalTile, Tile movementTile) {
        this.originalTile = originalTile;
        this.movementTile = movementTile;

    }

    public Tile getOpponentTile() {
        return opponentTile;
    }

    public void setOpponentTile(Tile opponentTile) {
        this.opponentTile = opponentTile;
    }

    public Tile getMovementTile() {
        return movementTile;
    }

    public void setMovementTile(Tile movementTile) {
        this.movementTile = movementTile;
    }

    public Tile getOriginalTile() { return originalTile; }

    public MoveType getPriorityType() {
        return type;
    }

    public void setType(MoveType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Type: %s <-> Opp tile: %s <-> Mov tile: %s", type.toString(), opponentTile.toString(), movementTile.toString());
    }
}
