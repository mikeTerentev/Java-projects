package expression;

public abstract class UnaryOperation implements TripleExpression{
    private TripleExpression expression;

    public UnaryOperation(TripleExpression expression){
        this.expression = expression;
    }

    public int evaluate(int x, int y, int z){
        return operation(expression.evaluate(x, y, z));
    }

    abstract protected int operation(int x);
}