package expression.Modes;

import expression.exceptions.InvalidConst;
import expression.exceptions.Overflow;
import expression.exceptions.InvalidOperation;

public class LongMode implements Mode<Long> {

    public Long parseNumber(final String number) throws InvalidConst {
        try {
            return Long.parseLong(number);
        } catch (NumberFormatException e) {
            throw new InvalidConst(number);
        }
    }

    public Long add(final Long x, final Long y) {
        return x + y;
    }

    public Long sub(final Long x, final Long y) {
        return x - y;
    }

    public Long mul(final Long x, final Long y) {
        return x * y;
    }

    public Long div(final Long x, final Long y) throws InvalidOperation, Overflow {
        if (y == 0) {
            throw new InvalidOperation("Division by zero");
        }
        return x / y;
    }

    public Long negate(final Long x) {
        return -x;
    }
}