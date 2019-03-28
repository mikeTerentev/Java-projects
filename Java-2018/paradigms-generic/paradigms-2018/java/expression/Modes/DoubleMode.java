package expression.Modes;

import expression.exceptions.InvalidConst;

public class DoubleMode implements Mode<Double> {
    public Double parseNumber(final String number) throws InvalidConst {
        try {
            return Double.parseDouble(number);
        } catch (NumberFormatException e) {
            throw new InvalidConst(number, 1);
        }
    }

    public Double add(final Double x, final Double y) {
        return x + y;
    }

    public Double sub(final Double x, final Double y) {
        return x - y;
    }

    public Double mul(final Double x, final Double y) {
        return x * y;
    }

    public Double div(final Double x, final Double y) {
        return x / y;
    }

    public Double negate(final Double x) {
        return -x;
    }
}
