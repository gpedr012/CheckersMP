package checkers.networkutils;

import java.util.Arrays;

public class Action {

    public enum Type {
        FIND_MATCH,
        CANCEL_MM,
        SERVER_INFO,
        FOUND_MATCH
    }

    private final Type type;
    private int[] args;
    private int argCtr = 0;

    public Action(Type type) {
        this.type = type;
    }

    public Action(Type type, int ... args) {
        this(type);
        this.args = new int[args.length];
        for (int arg :
                args) {
            this.args[argCtr++] = arg;
        }
    }

    public Type getType() {
        return type;
    }

    public int getArg(int idx) {
        return args[idx];
    }
}
