package expression.parser;


import expression.*;
import expression.Modes.Mode;
import expression.exceptions.MissingArg;
import expression.exceptions.Overflow;
import expression.exceptions.ParsingException;
import expression.exceptions.UnknownSymbol;

public class ExpressionParser<T> implements Parser<T> {
    private Token curToken;
    private Mode<T> mode;
    private Tokenizator<T> token;

    public ExpressionParser(final Mode<T> x) {
        mode = x;
    }

    private CommonExpression<T> unary() throws ParsingException {
        curToken = token.getNextToken();
        CommonExpression<T> result;
        switch (curToken) {
            case NUM:
                result = new Const<>(token.detectedNumber);
                curToken = token.getNextToken();
                break;
            case VAR:
                result = new Variable<>(token.getVarName());
                curToken = token.getNextToken();
                break;
            case NOT:
                result = new Negate<>(unary(), mode);
                break;
            case L_BRACE:
                result = detSubAdd();
                curToken = token.getNextToken();
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + Tokenizator.getExpression());
        }
        return result;
    }

    private CommonExpression<T> detMulOrDiv() throws ParsingException {
        CommonExpression<T> result = unary();
        do {
            switch (curToken) {
                case MUL:
                    result = new Multiply<>(result, unary(), mode);
                    break;
                case DIV:
                    result = new Divide<>(result, unary(), mode);
                    break;
                default:
                    return result;
            }
        } while (curToken != Token.END);
        return result;
    }

    private CommonExpression<T> detSubAdd() throws ParsingException {
        CommonExpression<T> result = detMulOrDiv();
        do {
            switch (curToken) {
                case ADD:
                    result = new Add<>(result, detMulOrDiv(), mode);
                    break;
                case SUB:
                    result = new Subtract<>(result, detMulOrDiv(), mode);
                    break;
                default:
                    return result;
            }
        } while (curToken != Token.END);
        return result;
    }


    public CommonExpression<T> parse(String expression, Mode<T> op) throws ParsingException {
        token = new Tokenizator<>(expression, op);
        return detSubAdd();
    }

}