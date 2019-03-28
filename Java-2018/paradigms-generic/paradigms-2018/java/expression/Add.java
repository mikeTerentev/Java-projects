package expression;

import expression.Modes.Mode;
import expression.exceptions.EvaluatingException;

public class Add<T> extends BinaryOperator<T> {
    public Add(CommonExpression<T> firstArgument, CommonExpression<T> secondArgument, Mode<T> mode) {
        super(firstArgument, secondArgument, mode);
    }


    public T calculate(T first, T second) throws EvaluatingException {
        return mode.add(first,second);
    }
}