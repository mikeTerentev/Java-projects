package expression.Modes;

import expression.exceptions.InvalidConst;
import expression.exceptions.Overflow;
import expression.exceptions.InvalidOperation;

public class IntegerMode implements Mode<Integer> {
    private boolean isChecked;

    public IntegerMode(boolean b) {
        isChecked = b;
    }

    public Integer parseNumber(final String number) throws InvalidConst {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new InvalidConst(number);
        }
    }

    private void checkAdd(final Integer x, final Integer y) throws Overflow {
        if (x > 0 && Integer.MAX_VALUE - x < y) {
            throw new Overflow();
        }
        if (x < 0 && Integer.MIN_VALUE - x > y) {
            throw new Overflow();
        }
    }

    public Integer add(final Integer x, final Integer y) throws Overflow {

        if (isChecked) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkSub(final Integer x, final Integer y) throws Overflow {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y) {
            throw new Overflow();
        }
        if (x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new Overflow();
        }
    }

    public Integer sub(final Integer x, final Integer y) throws Overflow {
        if (isChecked) {
            checkSub(x, y);
        }
        return x - y;
    }

    private void checkMul(final Integer x, final Integer y) throws Overflow {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y) {
            throw new Overflow();
        }
        if (x > 0 && y < 0 && Integer.MIN_VALUE / x > y) {
            throw new Overflow();
        }
        if (x < 0 && y > 0 && Integer.MIN_VALUE / y > x) {
            throw new Overflow();
        }
        if (x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new Overflow();
        }
    }

    public Integer mul(final Integer x, final Integer y) throws Overflow {
        if (isChecked) {
            checkMul(x, y);
        }
        return x * y;
    }

    private void checkDiv(final Integer x, final Integer y) throws Overflow {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new Overflow();
        }
    }

    public Integer div(final Integer x, final Integer y) throws InvalidOperation, Overflow {
        if (y == 0) {
            throw new InvalidOperation("Division by zero");
        }
        if (isChecked) {
            checkDiv(x, y);
        }
        return x / y;
    }

    private void checkNot(final Integer x) throws Overflow {
        if (x == Integer.MIN_VALUE) {
            throw new Overflow();
        }
    }

    public Integer negate(final Integer x) throws Overflow {
        if (isChecked) {
            checkNot(x);
        }
        return -x;
    }
}