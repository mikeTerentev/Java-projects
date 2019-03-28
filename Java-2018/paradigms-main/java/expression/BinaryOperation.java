package expression;

import com.sun.istack.internal.NotNull;

public abstract class BinaryOperation implements CommonExpression {
    private final CommonExpression first;
    private final @NotNull
    CommonExpression second;

    BinaryOperation(@NotNull CommonExpression first, CommonExpression second) {
        this.first = first;
        this.second = second;
    }


    public int evaluate(int x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }


    public double evaluate(double x) {
        return calculate(first.evaluate(x), second.evaluate(x));
    }


    public int evaluate(int x, int y, int z) {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    abstract protected int calculate(int one, int two);

    abstract protected double calculate(double one, double two);
}
