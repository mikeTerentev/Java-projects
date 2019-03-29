package expression;


import expression.exceptions.Overflow;
import expression.Modes.Mode;

public class Subtract<T> extends BinaryOperator<T> {
    public Subtract(CommonExpression<T> firstArgument, CommonExpression<T> secondArgument, Mode<T> mode) {
        super(firstArgument, secondArgument, mode);
    }

    protected T calculate(final T x, final T y) throws Overflow {
        return mode.sub(x, y);
    }
}