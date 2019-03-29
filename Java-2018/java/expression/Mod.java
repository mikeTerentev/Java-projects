package expression;

import com.sun.istack.internal.NotNull;

public class Mod extends BinaryOperation {
    public Mod(@NotNull CommonExpression first, @NotNull CommonExpression second) {
        super(first, second);
    }

    @Override
    public int calculate(int one, int two) {
        return one / two;
    }

    @Override
    protected double calculate(double one, double two) {
        return one / two;
    }
}
