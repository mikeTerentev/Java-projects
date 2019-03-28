package expression.parser;

import expression.exceptions.*;
import expression.Modes.Mode;

import java.util.*;


class Tokenizator<T> {
    private static String expression;
    private final Mode<T> mode;
    private Token prevToken;
    private String varName;
    private int balance, currentIndex;
    T detectedNumber;

    private static HashMap<String, Token> IDENTIFIER = new HashMap<>();

    static {
        IDENTIFIER.put("x", Token.VAR);
        IDENTIFIER.put("y", Token.VAR);
        IDENTIFIER.put("z", Token.VAR);
    }

    public static String getExpression() {
        return expression;
    }

    public T getDetectedNumber() {
        return detectedNumber;
    }

    String getVarName() {
        return varName;
    }

    Tokenizator(String expression, Mode<T> mode) {
        this.mode = mode;
        this.expression = expression;
        currentIndex = 0;
        balance = 0;
    }

    private void check(Token who) throws MissingArg {
        if (prevToken != Token.R_BRACE && prevToken != Token.VAR && prevToken != Token.NUM) {
            throw new MissingArg(expression, currentIndex);
        }
    }

    private void check() throws MissingArg {
        if (prevToken == Token.R_BRACE || prevToken == Token.VAR || prevToken == Token.NUM) {
            throw new MissingArg(expression, currentIndex);
        }
    }

    private void checkBalance() throws MissingArg {
        if (balance < 0) {
            throw new MissingArg(expression, currentIndex);
        }
    }


    Token getNextToken() throws ParsingException {
        Token curToken;
        while (currentIndex < expression.length() && Character.isWhitespace(expression.charAt(currentIndex))) {
            currentIndex++;
        }
        if (currentIndex >= expression.length()) {
            check(Token.END);
            if (balance != 0) {
                System.out.println(balance);
                throw new MissingArg(expression, currentIndex);
            }
            return Token.END;
        }
        char nowHave = expression.charAt(currentIndex);
        switch (nowHave) {
            case '+':
                check(Token.ADD);
                curToken = Token.ADD;
                break;
            case '-':
                if (prevToken == Token.R_BRACE || prevToken == Token.VAR || prevToken == Token.NUM) {
                    curToken = Token.SUB;
                } else {
                    if (currentIndex + 1 < expression.length() && Character.isDigit(expression.charAt(currentIndex + 1))) {
                        detectedNumber = detectNumber();
                        currentIndex--;
                        curToken = Token.NUM;
                    } else {
                        curToken = Token.NOT;
                    }
                }
                break;
            case '*':
                check(Token.MUL);
                curToken = Token.MUL;
                break;
            case '/':
                check(Token.DIV);
                curToken = Token.DIV;
                break;
            case '(':
                check();
                curToken = Token.L_BRACE;
                balance++;
                break;
            case ')':
                check(Token.R_BRACE);
                curToken = Token.R_BRACE;
                balance--;
                break;
            default:
                if (Character.isDigit(expression.charAt(currentIndex))) {
                    int from = currentIndex;
                    while (currentIndex < expression.length() && canNumberUse(expression.charAt(currentIndex))) {
                        currentIndex++;
                    }
                    int to = currentIndex;
                    try {
                        detectedNumber = mode.parseNumber(expression.substring(from, to));
                    } catch (NumberFormatException e) {
                        throw new InvalidConst(expression, currentIndex);
                    }
                    if (currentIndex != expression.length())
                        currentIndex--;
                    curToken = Token.NUM;
                } else {
                    curToken = tryToDetectIdn();
                }
        }
        //System.out.println(prevToken + " " + curToken);
        checkBalance();
        currentIndex++;
        prevToken = curToken;
        return curToken;
    }

    private T detectNumber() throws InvalidConst {
        int from = currentIndex;
        currentIndex++;
        while (currentIndex < expression.length() &&
                (Character.isDigit(expression.charAt(currentIndex)) || canNumberUse(expression.charAt(currentIndex)))) {
            currentIndex++;
        }
        try {
            return detectedNumber = mode.parseNumber(expression.substring(from, currentIndex));
        } catch (NumberFormatException e) {
            throw new InvalidConst(expression, currentIndex);
        }
    }

    private boolean canNumberUse(final char x) {
        return Character.isDigit(x) || x == '.' || x == 'e' || x == '-';
    }

    private boolean isIdentifier(char now) {
        return Character.isLetterOrDigit(now);//
    }

    private Token tryToDetectIdn() throws UnknownIdentifier {
        int start = currentIndex;
        if (!Character.isLetter(expression.charAt(start))) {
            throw new UnknownIdentifier(expression, getMoreInfo(start), start);
        }
        while (currentIndex < expression.length() && isIdentifier(expression.charAt(currentIndex))) {
            currentIndex++;
        }
        String detectedInd = expression.substring(start, currentIndex);
        currentIndex--;
        Token detectedToken = IDENTIFIER.get(detectedInd);
        if (detectedToken == null) {
            throw new UnknownIdentifier(expression, getMoreInfo(start), start);
        }
        if (detectedToken == Token.VAR) {
            varName = detectedInd;
        }
        return detectedToken;
    }

    private StringBuilder getMoreInfo(int position) {
        StringBuilder out = new StringBuilder("");
        for (int i = 0; i < position - 1; i++) {
            out.append(" ");
        }
        out.append("^");
        return out;
    }
}