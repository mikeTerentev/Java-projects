package expression;


import expression.exceptions.InvalidOperation;

public class CheckedLog extends UnaryOperator {
    public CheckedLog(final CommonExpression x) {
        super(x);
    }

    protected void check(final int x) throws InvalidOperation {
        if (x <= 0) {
            throw new InvalidOperation("Log form negative");
        }
    }

    protected int calculate(final int x) throws InvalidOperation {
        check(x);
        int l = 0, r = 31;
        while (r - l > 1) {
            int m = (l + r) >> 1;
            try {
                int res = new CheckedPow(new Const(m)).evaluate(0, 0, 0);
                if (res <= x) {
                    l = m;
                } else {
                    r = m;
                }
            } catch (Exception e) {
                r = m;
            }
        }
        return l;
    }
}