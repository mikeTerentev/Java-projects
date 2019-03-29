package expression.parser;


import expression.*;
import expression.exceptions.ParsingException;

public class ExpressionParser implements Parser {
    private Token curToken;

    private Tokenizator token;

    private CommonExpression unary() throws ParsingException {
        curToken = token.getNextToken();
        CommonExpression result;
        switch (curToken) {

            case NUM:
                result = new Const(token.getDetectedNumber());
                curToken = token.getNextToken();
                break;
            case VAR:
                result = new Variable(token.getVarName());
                curToken = token.getNextToken();
                break;
            case NOT:
                result = new CheckedNegate(unary());
                break;
            case L_BRACE:
                result = detSubAdd();
                curToken = token.getNextToken();
                break;
            case LOG_10:
                result = new CheckedLog(unary());
                break;
            case POW_10:
                result = new CheckedPow(unary());
                break;
            default:
                throw new ParsingException("Incorrect expression" + "\n" + Tokenizator.getExpression());
        }
        return result;
    }

    private CommonExpression detMulOrDiv() throws ParsingException {
        CommonExpression result = unary();
        do {
            switch (curToken) {
                case MUL:
                    result = new CheckedMultiply(result, unary());
                    break;
                case DIV:
                    result = new CheckedDivide(result, unary());
                    break;
                default:
                    return result;
            }
        } while (curToken != Token.END);
        return result;
    }

    private CommonExpression detSubAdd() throws ParsingException {
        CommonExpression result = detMulOrDiv();
        do {
            switch (curToken) {
                case ADD:
                    result = new CheckedAdd(result, detMulOrDiv());
                    break;
                case SUB:
                    result = new CheckedSubtract(result, detMulOrDiv());
                    break;
                default:
                    return result;
            }
        } while (curToken != Token.END);
        return result;
    }


    @Override
    public CommonExpression parse(String expression) throws ParsingException {
        token = new Tokenizator(expression);
        return detSubAdd();
    }
}