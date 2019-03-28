package expression;


import expression.Modes.Mode;
import expression.exceptions.EvaluatingException;

public abstract class UnaryOperator<T> implements CommonExpression<T> {
    private CommonExpression<T> expression;
    protected final Mode<T> mode;

    UnaryOperator(CommonExpression<T> expression, Mode<T> mode) {

        this.expression = expression;
        this.mode = mode;
    }

    public T evaluate(T x, T y, T z) throws EvaluatingException {
        return calculate(expression.evaluate(x, y, z));
    }

    abstract protected T calculate(T x) throws EvaluatingException;

}