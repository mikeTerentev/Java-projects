package expression;


import com.sun.istack.internal.NotNull;

public class Add extends BinaryOperation {
    public Add(@NotNull CommonExpression first, @NotNull CommonExpression second) {
        super(first, second);
    }

    public int calculate(int one, int two) {
        return one + two;
    }

    public double calculate(double one, double two) {
        return one + two;
    }

}
