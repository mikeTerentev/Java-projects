package expression;

public class Const implements TripleExpression {
    private Number value;

    public Const(Number value) {
        this.value = value;
    }


    public int evaluate(int x) {
        return value.intValue();
    }


    @Override
    public int evaluate(int x, int y, int z) {
        return value.intValue();
    }
}
