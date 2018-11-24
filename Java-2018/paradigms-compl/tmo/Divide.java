package expression;

import com.sun.istack.internal.NotNull;

public class Divide extends BinaryOperation {
    public Divide(TripleExpression first, TripleExpression second) {
        super(first, second);
    }

    @Override
    public int calculate(int one, int two) {
        assert two != 0;
        return one / two;
    }

}
