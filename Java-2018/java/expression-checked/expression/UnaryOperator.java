package expression;

import expression.exceptions.EvaluatingException;

public abstract class UnaryOperator implements CommonExpression {
    private CommonExpression expression;

    public UnaryOperator(CommonExpression expression) {
        this.expression = expression;
    }

    public int evaluate(int x, int y, int z) throws EvaluatingException {
        return calculate(expression.evaluate(x, y, z));
    }

    abstract protected int calculate(int x) throws EvaluatingException;

    protected abstract void check(final int x) throws EvaluatingException;
}