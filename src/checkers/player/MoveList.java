package checkers.player;

import checkers.ui.Tile;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoveList
{
    enum MovePriority
    {
        OPTIONAL, REQUIRED, EMPTY
    }

    private List<Tile> tiles;
    private MovePriority priority;



    public MoveList(List<Tile> tiles)
    {
        this.tiles = tiles;
        this.priority = MovePriority.EMPTY;
    }

    public MoveList(List<Tile> tiles, MovePriority priority)
    {
        this.tiles = tiles;
        this.priority = priority;

    }

    public MoveList()
    {
        this(new ArrayList<>(4));
    }

    public void addAll(MoveList incomingList)
    {
        switch(priority)
        {
            case REQUIRED:
                break;
            case OPTIONAL:
                if(incomingList.priority == MovePriority.REQUIRED)
                {
                    tiles.clear();
                }
                tiles.addAll(incomingList.tiles);
                break;
            default:
                tiles.addAll(incomingList.tiles);
        }
    }

    public void addMove(Tile tile)
    {
        tiles.add(tile);
    }

    public void clear()
    {
        tiles.clear();
    }

    public boolean isEmpty()
    {
        return tiles.isEmpty();
    }

    public MovePriority getPriority()
    {
        return priority;
    }

    public void setPriority(MovePriority priority)
    {

        this.priority = priority;
    }

    public Iterator<Tile> iterator()
    {
        return tiles.iterator();
    }


}
