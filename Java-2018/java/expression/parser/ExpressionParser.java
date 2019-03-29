package expression.parser;

import expression.*;


public class ExpressionParser implements Parser {
    private String expression, name;
    private int index, constNumber;
    private TokensLidruary token;

    public CommonExpression parse(String expression) {
        this.expression = expression;
        index = 0;
        return detectedAddOrSub();
    }

    private CommonExpression detectedAddOrSub() {
        CommonExpression operand = detectedMulOrDivOrMod();
        while (true) {
            if (token == TokensLidruary.add) {
                operand = new Add(operand, detectedMulOrDivOrMod());
            } else if (token == TokensLidruary.sub) {
                operand = new Subtract(operand, detectedMulOrDivOrMod());
            } else {
                return operand;
            }
        }
    }

    private CommonExpression detectedMulOrDivOrMod() {
        CommonExpression expression = detectedOrConstOrUnOrVar();
        while (true) {
            if (token == TokensLidruary.div) {
                expression = new Divide(expression, detectedOrConstOrUnOrVar());
            } else if (token == TokensLidruary.mod) {
                expression = new Mod(expression, detectedOrConstOrUnOrVar());
            } else if (token == TokensLidruary.mul) {
                expression = new Multiply(expression, detectedOrConstOrUnOrVar());
            } else {
                return expression;
            }
        }
    }

    private CommonExpression detectedOrConstOrUnOrVar() {
        nextCharacter();
        CommonExpression operand = new Const(0);
        if (token == TokensLidruary.variable) {
            operand = new Variable(name);
            nextCharacter();
        } else if (token == TokensLidruary.constant) {
            operand = new Const(constNumber);
            nextCharacter();
        } else if (token == TokensLidruary.sub) {
            operand = new UnaryMinus(detectedOrConstOrUnOrVar());
        } else if (token == TokensLidruary.leftBracket) {
            operand = detectedAddOrSub();
            nextCharacter();
        }
        return operand;
    }

    private void nextWord() {
        while (Character.isWhitespace(getNextChar())) {
        }
        index--;
    }

    private void nextCharacter() {
        nextWord();
        char character = getNextChar();
        if (Character.isDigit(character)) {
            StringBuilder str = new StringBuilder();
            while (Character.isDigit(character)) {
                str.append(character);
                character = getNextChar();
            }
            index--;
            constNumber = Integer.parseUnsignedInt(str.toString());
            token = TokensLidruary.constant;
        } else if (character == '(') {
            token = TokensLidruary.leftBracket;
        } else if (character == ')') {
            token = TokensLidruary.rightBracket;
        } else if (character == '+') {
            token = TokensLidruary.add;
        } else if (character == '-') {
            token = TokensLidruary.sub;
        } else if (character == '*') {
            token = TokensLidruary.mul;
        } else if (character == '/') {
            token = TokensLidruary.div;
        } else if (character == 'x' || character == 'y' || character == 'z') {
            token = TokensLidruary.variable;
            name = Character.toString(character);
        } else if (index < expression.length()) {
            if (expression.substring(index - 1, index + 2).equals("mod")) {
                token = TokensLidruary.mod;
                index += 2;
            }
        }
        nextWord();
    }

    private char getNextChar() {
        if (index < expression.length()) {
            char ret = expression.charAt(index);
            index++;
            return ret;
        }
        return '@';
    }


}