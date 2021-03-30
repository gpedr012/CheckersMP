package checkers.client.game;

import checkers.client.ui.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveList {


    private MoveType priorityType;
    private List<Move> moves;


    public MoveList(List<Move> moves, MoveType priorityType) {

        this.priorityType = priorityType;
        this.moves = moves;

    }

    public MoveList() {

        this(new ArrayList<>(4), MoveType.EMPTY);

    }

    public void addAll(MoveList incomingList) {
        switch (priorityType) {
            case REQUIRED:
                if (incomingList.priorityType == MoveType.REQUIRED) {
                    moves.addAll(incomingList.moves);
                }
                break;
            case OPTIONAL:
                if (incomingList.priorityType == MoveType.REQUIRED) {
                    moves.clear();
                    this.priorityType = MoveType.REQUIRED;
                }
                moves.addAll(incomingList.moves);

                break;
            case EMPTY:
                this.moves = incomingList.moves;
                this.priorityType = incomingList.priorityType;
                break;
            default:
                moves.addAll(incomingList.moves);
        }
    }

    public void addMove(Move move) {
        moves.add(move);
        this.priorityType = move.getPriorityType();

    }


    public void clear() {
        priorityType = MoveType.EMPTY;
        moves.clear();
    }

    public boolean isEmpty() {
        return priorityType == MoveType.EMPTY;

    }

    public MoveType getPriority() {
        return priorityType;
    }

    public void setPriority(MoveType moveType) {

        this.priorityType = moveType;
    }

    public Move findMove(Tile tile) {
        for (Move move : moves) {
            if (move.getMovementTile() == tile) {
                return move;
            }
        }

        return null;
    }

    public int size() {
        return moves.size();
    }

    public Move get(int index) {
        return moves.get(index);
    }

    public Iterator<Move> iterator() {
        return moves.iterator();
    }


}
