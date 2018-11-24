package expression;


import expression.exceptions.Overflow;

public class CheckedSubtract extends BinaryOperator {
    public CheckedSubtract(final CommonExpression x, final CommonExpression y) {
        super(x, y);
    }

    protected void check(final int x, final int y) throws Overflow {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new Overflow();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new Overflow();
        }
    }

    protected int calculate(final int x, final int y) throws Overflow {
        check(x, y);
        return x - y;
    }
}
