package expression.exceptions;

import expression.CommonExpression;
import expression.parser.ExpressionParser;

public class Main {
    public static void main(String[] args) throws Exception {
        ExpressionParser parser = new ExpressionParser();
        CommonExpression res = parser.parse("10 + 4 / 2 - 7");
        System.out.println(res.evaluate(0, 0, 0));
    }
}
