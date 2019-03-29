package expression;

import expression.Modes.Mode;
import expression.exceptions.Overflow;


public class Negate<T> extends UnaryOperator<T> {
    public Negate(CommonExpression<T> argument, Mode<T> mode) {
        super(argument, mode);
    }


    public T calculate(T argument) throws Overflow {
        return mode.negate(argument);
    }
}