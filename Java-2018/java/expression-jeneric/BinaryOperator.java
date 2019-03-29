package expression;

import com.sun.istack.internal.NotNull;
import expression.Modes.Mode;
import expression.exceptions.EvaluatingException;
import expression.exceptions.Overflow;
import expression.exceptions.ParsingException;


public abstract class BinaryOperator<T> implements CommonExpression<T> {
    private final CommonExpression<T> first;
    private final CommonExpression<T> second;
    protected final Mode<T> mode;

    BinaryOperator( CommonExpression<T> first, CommonExpression<T> second, final Mode<T> mode) {
        this.first = first;
        this.second = second;
        this.mode = mode;
    }

    public T evaluate(T x, T y, T z) throws EvaluatingException {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    abstract protected T calculate(T one, T two) throws EvaluatingException;


}
