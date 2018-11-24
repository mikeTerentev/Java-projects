package expression;

import com.sun.istack.internal.NotNull;

import java.util.NoSuchElementException;

public class Variable implements CommonExpression {
    private final String name;

    public Variable(@NotNull String name) {
        this.name = name;
    }

    @Override
    public final int evaluate(int x) {
        return x;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        switch (name) {
            case "x":
                return x;
            case "y":
                return y;
            case "z":
                return z;
            default:
                throw new NoSuchElementException("No requested variable: " + name);
        }
    }
}
