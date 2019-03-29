package expression;

public abstract class UnaryOperation implements CommonExpression {
    private CommonExpression expression;

    public UnaryOperation(CommonExpression expression) {
        this.expression = expression;
    }

    public int evaluate(int x, int y, int z) {
        return operation(expression.evaluate(x, y, z));
    }

    public int evaluate(int x) {
        return operation(expression.evaluate(x));
    }

    public double evaluate(double x) {
        return x;
    }

    abstract protected int operation(int x);
}