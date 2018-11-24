package expression;

import expression.exceptions.InvalidOperation;

public class UnaryMinus extends UnaryOperator {

    public UnaryMinus(CommonExpression expression){
        super(expression);
    }

    public int calculate(int value){
        return -1 * value;
    }

    @Override
    protected void check(int x) throws InvalidOperation {

    }

}