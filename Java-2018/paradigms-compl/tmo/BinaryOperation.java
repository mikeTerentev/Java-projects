package expression;
import com.sun.istack.internal.NotNull;

public abstract class BinaryOperation implements TripleExpression {
    private final TripleExpression first;
    private final @NotNull TripleExpression second;

    BinaryOperation(@NotNull TripleExpression first,  TripleExpression second) {
        this.first = first;
        this.second = second;
    }


    public int evaluate(int x, int y, int z) {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    abstract protected int calculate(int one, int two);

}
