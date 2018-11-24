package expression;

import com.sun.istack.internal.NotNull;
import expression.exceptions.EvaluatingException;
import expression.exceptions.Overflow;
import expression.exceptions.ParsingException;

public abstract class BinaryOperator implements CommonExpression {
    private final CommonExpression first;
    private final @NotNull
    CommonExpression second;

    BinaryOperator(@NotNull CommonExpression first, CommonExpression second) {
        this.first = first;
        this.second = second;
    }

    public int evaluate(int x, int y, int z)throws EvaluatingException {
        return calculate(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    abstract protected int calculate(int one, int two) throws Overflow ;

    protected abstract void check(int first, int second) throws Overflow;

}
