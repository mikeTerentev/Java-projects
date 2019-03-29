package expression;

import expression.exceptions.*;

public class CheckedAdd extends BinaryOperator {
    public CheckedAdd(CommonExpression firstArgument, CommonExpression secondArgument) {
        super(firstArgument, secondArgument);
    }


    public int calculate(int first, int second) throws Overflow {
        check(first, second);
        return first + second;
    }


    protected void check(int first, int second) throws Overflow {
        if (first > 0 && second > Integer.MAX_VALUE - first) {
            throw new Overflow();
        }
        if (first < 0 && second < Integer.MIN_VALUE - first) {
            throw new Overflow();
        }
    }
}