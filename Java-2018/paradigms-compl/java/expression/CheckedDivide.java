package expression;

import expression.exceptions.*;


public class CheckedDivide extends BinaryOperator {

    public CheckedDivide(CommonExpression firstArgument, CommonExpression secondArgument) {
        super(firstArgument, secondArgument);
    }

    @Override
    public int calculate(int first, int second) throws Overflow{
        check(first, second);
        return first / second;
    }

    @Override
    protected void check(int first, int second) throws Overflow {
        if (second == 0) {
            throw new Overflow();
        }
        if (first == Integer.MIN_VALUE && second == -1) {
            throw new Overflow();
        }
    }
}