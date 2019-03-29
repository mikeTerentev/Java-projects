package expression;

import expression.exceptions.*;


public class CheckedNegate extends UnaryOperator {
    public CheckedNegate(CommonExpression argument) {
        super(argument);
    }


    public int calculate(int argument) throws Overflow{
        check(argument);
        return -argument;
    }

    protected void check(int argument) throws Overflow{
        if (argument == Integer.MIN_VALUE) {
            throw new Overflow();
        }
    }
}