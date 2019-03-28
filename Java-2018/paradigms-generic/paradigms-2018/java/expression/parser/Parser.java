package expression.parser;

import expression.TripleExpression;
import expression.Modes.Mode;
import expression.exceptions.ParsingException;

import java.text.ParseException;

/**
 * @author Georgiy Korneev (kgeorgiy@kgeorgiy.info)
 */
public interface Parser<T> {
    TripleExpression parse(String expression, Mode<T> x) throws ParsingException;
}
