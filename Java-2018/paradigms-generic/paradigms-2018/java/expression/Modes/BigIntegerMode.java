package expression.Modes;


import expression.exceptions.InvalidConst;
import expression.exceptions.InvalidOperation;


import java.math.BigInteger;

public class BigIntegerMode implements Mode<BigInteger> {
    public BigInteger parseNumber(final String number) throws InvalidConst {
        try {
            return new BigInteger(number);
        } catch (NumberFormatException e) {
            throw new InvalidConst(number);
        }
    }

    private void checkZero(final BigInteger x) throws InvalidOperation {
        if (x.equals(BigInteger.ZERO)) {
            throw new InvalidOperation("Division by zero");
        }
    }

    public BigInteger div(final BigInteger x, final BigInteger y) throws InvalidOperation {
        checkZero(y);
        return x.divide(y);
    }

    public BigInteger add(final BigInteger x, final BigInteger y) {
        return x.add(y);
    }

    public BigInteger sub(final BigInteger x, final BigInteger y) {
        return x.subtract(y);
    }

    public BigInteger mul(final BigInteger x, final BigInteger y) {
        return x.multiply(y);
    }

    public BigInteger negate(final BigInteger x) {
        return x.negate();
    }

}