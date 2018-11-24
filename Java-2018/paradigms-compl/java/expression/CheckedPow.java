package expression;

import expression.exceptions.Overflow;
import expression.exceptions.InvalidOperation;

public class CheckedPow extends UnaryOperator {
    public CheckedPow(final CommonExpression x) {
        super(x);
    }


    protected void check(int y) throws InvalidOperation,Overflow {
        if (y < 0) {
            throw new InvalidOperation("Power error =" + y);
        }
        if (y > 9) {
            throw new Overflow();
        }
    }
    protected int calculate(int y) throws Overflow, InvalidOperation {
        check(y);
        int res = 1;
        for (int i = 0; i < y; i++) {
            res *= 10;
        }
        return res;
    }
}