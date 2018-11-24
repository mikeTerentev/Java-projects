package expression.parser;

import expression.CommonExpression;
import expression.TripleExpression;
import expression.exceptions.ParsingException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser {
    TripleExpression parse(String expression) throws ParsingException;
}
