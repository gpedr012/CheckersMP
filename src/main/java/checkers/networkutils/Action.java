package checkers.networkutils;

import java.util.Arrays;

public class Action {

    public enum Type {
        FIND_MATCH,
        CANCEL_MM,
        SERVER_INFO,
        FOUND_MATCH,
        HAS_TURN,
        MOVE_NO_ELIM,
        MOVE_ELIM
    }

    private final Type type;
    private int[] args;

    public Action(Type type) {
        this.type = type;
    }

    public Action(Type type, int[] args) {
        this(type);
        this.args = args;
    }

    public Type getType() {
        return type;
    }

    public int getArg(int idx) {
        return args[idx];
    }

    public int[] getAllArgs() {
        return args;

    }
}
