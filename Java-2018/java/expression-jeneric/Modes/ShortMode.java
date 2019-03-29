package expression.Modes;

import expression.exceptions.InvalidConst;
import expression.exceptions.Overflow;
import expression.exceptions.InvalidOperation;

public class ShortMode implements Mode<Short> {

    public Short parseNumber(final String number) throws InvalidConst {
        try {
            return (short) Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new InvalidConst(number);
        }
    }

    public Short add(final Short x, final Short y) {
        return (short) (x + y);
    }

    public Short sub(final Short x, final Short y) {
        return (short) (x - y);
    }

    public Short mul(final Short x, final Short y) {
        return (short) (x * y);
    }

    public Short div(final Short x, final Short y) throws InvalidOperation {
        if (y == 0) {
            throw new InvalidOperation("Division by zero");
        }
        return (short) (x / y);
    }

    public Short negate(final Short x) {
        return (short) (-x);
    }
}