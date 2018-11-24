package expression.parser;

import expression.exceptions.*;


class NextToken {
    private final String expression;
    private int curIndex, balance, value;
    private Token prevToken;


    NextToken(String expression) {
        this.expression = expression;
        curIndex = 0;
    }

    Token getNextToken(ExpressionParser name) throws Exception {
        Token curToken;
        while (curIndex < expression.length() &&
                Character.isWhitespace(expression.charAt(curIndex))) {
            curIndex++;
        }
        if (curIndex >= expression.length()) {
            checkOperand(Token.end);
            if (balance != 0) {
                System.out.println(balance);
                throw new MissingArg(expression, getString(curIndex), curIndex);
            }
            return Token.end;
        }
        char curCharacter = expression.charAt(curIndex);
        switch (curCharacter) {
            case '+':
                checkOperand(Token.add);
                curToken = Token.add;
                break;
            case '-':
                if (prevToken == Token.rBrace || prevToken == Token.var || prevToken == Token.num) {
                    checkOperand(Token.not);
                    curToken = Token.sub;
                } else {
                    if (curIndex + 1 < expression.length() && Character.isDigit(expression.charAt(curIndex + 1))) {
                        curIndex--;
                        curToken = Token.num;
                    } else {
                        curToken = Token.not;
                    }
                }
                break;
            case '*':
                checkOperand(Token.mul);
                curToken = Token.mul;
                break;
            case '/':
                checkOperand(Token.div);
                curToken = Token.div;
                break;
            case '|':
                checkOperand(Token.or);
                curToken = Token.or;
                break;
            case '&':
                checkOperand(Token.and);
                curToken = Token.and;
                break;
            case '^':
                checkOperand(Token.xor);
                curToken = Token.xor;
                break;
            case '(':
                checkOperation();
                curToken = Token.lBrace;
                balance++;
                break;
            case ')':
                checkOperand(Token.rBrace);
                curToken = Token.rBrace;
                balance--;
                break;
            default:
                if (Character.isDigit(expression.charAt(curIndex))) {
                    int from = curIndex;
                    while (curIndex < expression.length() && Character.isDigit(expression.charAt(curIndex))) {
                        curIndex++;
                    }
                    int to = curIndex;
                    int value = Integer.parseUnsignedInt(expression.substring(from, to));
                    name.setValue(value);
                    if (curIndex != expression.length())
                        curIndex--;
                    curToken = Token.num;
                } else if (curCharacter == 'x' || curCharacter == 'y' || curCharacter == 'z') {
                    curToken = Token.var;
                    name.setName(String.valueOf(curCharacter));
                } else {
                    throw new UnknownSymbol();
                }
        }
        checkBalance();
        curIndex++;
        prevToken = curToken;
        return curToken;
    }

    private void checkOperand(Token who) throws Exception {
        if (prevToken != Token.rBrace && prevToken != Token.var && prevToken != Token.num) {

            throw new MissingArg(expression, getString(curIndex), curIndex);

        }
    }

    private void checkOperation() throws Exception {
        if (prevToken == Token.rBrace || prevToken == Token.var || prevToken == Token.num) {
            throw new MissingArg(expression, getString(curIndex), curIndex);
        }
    }

    private StringBuilder getString(int position) {
        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < position; i++) {
            out.append(" ");
        }
        out.append("^");
        return out;
    }

    private void checkBalance() throws Exception {
        if (balance < 0) {
            throw new MissingArg(expression, getString(curIndex), curIndex);
        }
    }

}