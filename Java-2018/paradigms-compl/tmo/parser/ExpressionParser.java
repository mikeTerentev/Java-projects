package expression.parser;

import expression.*;


public class ExpressionParser implements Parser {

    private String expression, variableName;
    private int index, constValue;
    private TokensLidruary cur;

    private char nextCharacter() {
        if (index < expression.length()) {
            char ret = expression.charAt(index);
            index++;
            return ret;
        } else {
            return '@';
        }
    }

    private void nextWord() {
        while (Character.isWhitespace(nextCharacter())) {

        }
        index--;
    }

    private void next() {
        nextWord();
        char ch = nextCharacter();
        if (Character.isDigit(ch)) {
            StringBuilder str = new StringBuilder();
            while (Character.isDigit(ch)) {
                str.append(ch);
                ch = nextCharacter();
            }
            index--;
            constValue = Integer.parseUnsignedInt(str.toString());
            cur = TokensLidruary.constant;
        } else if (ch == '^') {
            cur = TokensLidruary.xor;
        } else if (ch == '|') {
            cur = TokensLidruary.or;
        } else if (ch == '&') {
            cur = TokensLidruary.and;
        } else if (ch == '+') {
            cur = TokensLidruary.add;
        } else if (ch == '-') {
            cur = TokensLidruary.sub;
        } else if (ch == '*') {
            cur = TokensLidruary.mul;
        } else if (ch == '/') {
            cur = TokensLidruary.div;
        } else if (ch == '(') {
            cur = TokensLidruary.leftBracket;
        } else if (ch == ')') {
            cur = TokensLidruary.rightBracket;
        } else if (ch == 'x' || ch == 'y' || ch == 'z') {
            cur = TokensLidruary.variable;
            variableName = Character.toString(ch);
        }
        nextWord();

    }


    private CommonExpression un() {
        next();
        CommonExpression operand = new Const(0);
        if (cur == TokensLidruary.constant) {
            operand = new Const(constValue);
            next();
        } else if (cur == TokensLidruary.sub) {
            operand = new UnaryMinus(un());
        } else if (cur == TokensLidruary.variable) {
            operand = new Variable(variableName);
            next();
        } else if (cur == TokensLidruary.leftBracket) {
            operand = detOr();
            next();
        }
        return operand;
    }

    private CommonExpression MulOrDivOrMod() {
        CommonExpression operand = un();
        while (true) {
            if (cur == TokensLidruary.mul) {
                operand = new Multiply(operand, un());
            } else if (cur == TokensLidruary.div) {
                operand = new Divide(operand, un());
            } else if (cur == TokensLidruary.mod) {
                operand = new Mod(operand, un());
            } else {
                return operand;
            }
        }
    }

    private CommonExpression AddOrSub() {
        CommonExpression calc = MulOrDivOrMod();
        while (true) {
            if (cur == TokensLidruary.add) {
                calc = new Add(calc, MulOrDivOrMod());
            } else if (cur == TokensLidruary.sub) {
                calc = new Subtract(calc, MulOrDivOrMod());
            } else {
                return calc;
            }
        }
    }

    private CommonExpression detAnd() {
        CommonExpression calc = AddOrSub();
        while (true) {
            if (cur == TokensLidruary.and) {
                calc = new And(calc, AddOrSub());
            } else {
                return calc;
            }
        }
    }

    private CommonExpression detXor() {
        CommonExpression calc = detAnd();
        while (true) {
            if (cur == TokensLidruary.xor) {
                calc = new Xor(calc, detAnd());
            } else {
                return calc;
            }
        }
    }

    private CommonExpression detOr() {
        CommonExpression calc = detXor();
        while (true) {
            if (cur == TokensLidruary.or) {
                calc = new Or(calc, detXor());
            } else {
                return calc;
            }
        }
    }


    public CommonExpression parse(String expression) {
        this.expression = expression;
        index = 0;
        return detOr();
    }
}