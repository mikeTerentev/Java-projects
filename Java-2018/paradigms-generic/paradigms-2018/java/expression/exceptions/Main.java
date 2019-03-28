package expression.exceptions;

import expression.CommonExpression;
import expression.Modes.DoubleMode;
import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) throws Exception {
        ExpressionParser<Double> parser = new ExpressionParser<>(new DoubleMode());
        CommonExpression<Double> res = parser.parse("1e-3  - 1e-3",new DoubleMode());
        System.out.println(res.evaluate(0.0,0.0,0.0));
    }
}
