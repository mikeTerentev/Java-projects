package expression;

import expression.Modes.Mode;
import expression.exceptions.EvaluatingException;
import expression.exceptions.InvalidConst;
import expression.exceptions.InvalidOperation;


public class Divide<T> extends BinaryOperator<T> {

    public Divide(CommonExpression<T> firstArgument, CommonExpression<T> secondArgument, Mode<T> mode) {
        super(firstArgument, secondArgument, mode);
    }

    @Override
    public T calculate(T first, T second) throws EvaluatingException {
        return mode.div(first, second);
    }


}