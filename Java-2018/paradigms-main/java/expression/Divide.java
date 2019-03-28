package expression;

import com.sun.istack.internal.NotNull;

public class Divide extends BinaryOperation {

    public Divide(CommonExpression first,CommonExpression second) {
        super(first, second);
    }

    @Override
    public int calculate(int one, int two) {

        return one / two;
    }


    protected double calculate(double one, double two) {
        return one / two;
    }
}
