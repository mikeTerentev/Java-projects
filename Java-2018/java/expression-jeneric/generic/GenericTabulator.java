package expression.generic;

import expression.Modes.*;
import expression.TripleExpression;
import expression.exceptions.InvalidModeType;
import expression.exceptions.ParsingException;
import expression.parser.ExpressionParser;
import expression.parser.Parser;

import java.util.HashMap;

public class GenericTabulator implements Tabulator {
    private final static HashMap<String, Mode<?>> MODES = new HashMap<>();

    static {
        MODES.put("i", new IntegerMode(true));
        MODES.put("bi", new BigIntegerMode());
        MODES.put("d", new DoubleMode());
        MODES.put("u", new IntegerMode(false));
        MODES.put("l", new LongMode());
        MODES.put("s", new ShortMode());
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2) throws InvalidModeType {
        return build(getMode(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Mode<?> getMode(final String mode) throws InvalidModeType {
        Mode<?> result = MODES.get(mode);
        if (result != null) {
            return result;
        }
        throw new InvalidModeType(mode);
    }

    private <T> Object[][][] build(Mode<T> op, String expression, int x1, int x2, int y1, int y2, int z1, int z2) {
        Object[][][] res = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        Parser<T> parser = new ExpressionParser<>(op);
        TripleExpression exp;
        try {
            exp = parser.parse(expression, op);
        } catch (ParsingException e) {
            return res;
        }
        for (int i = x1; i <= x2; ++i) {
            for (int j = y1; j <= y2; ++j) {
                for (int k = z1; k <= z2; ++k) {
                    try {
                        res[i - x1][j - y1][k - z1] = exp.evaluate(op.parseNumber(Integer.toString(i)), op.parseNumber(Integer.toString(j)), op.parseNumber(Integer.toString(k)));
                    } catch (Exception e) {
                        res[i - x1][j - y1][k - z1] = null;
                    }
                }
            }
        }
        return res;
    }
}
