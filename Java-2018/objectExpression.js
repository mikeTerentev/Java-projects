"use strict";

function Operation() {
    this.first = arguments[0];
    this.second = arguments[1];
}

function UnaryOperation() {
    this.first = arguments[0];
}

Operation.prototype.evaluate = function () {
    var left = this.first.evaluate.apply(this.first, arguments);
    var right = this.second.evaluate.apply(this.second, arguments);
    return this.doOperation.apply(null, [left, right]);
};
UnaryOperation.prototype.evaluate = function () {
    return this.doOperation.call(null, this.first.evaluate.apply(this.first, arguments));
};

Operation.prototype.toString = function () {
    var builder = this.first.toString() + ' ' + this.second.toString();
    return builder + ' ' + this.operation;
};
Operation.prototype.prefix = function () {
    var builder = '(' + this.operation + ' ';
    builder += this.first.prefix() + ' ';
    builder += this.second.prefix() + ')';
    return builder;
};
UnaryOperation.prototype.toString = function () {
    var builder = this.first.toString();
    return builder + ' ' + this.operation;

};
UnaryOperation.prototype.prefix = function () {
    //print(this.operation + 'mknompok');
    var builder = '(' + this.operation + ' ';
    builder += this.first.prefix() + ')';
    return builder;
};

function Const(val) {
    this.val = val;
}

Const.prototype.evaluate = function () {
    return this.val;
};
Const.prototype.prefix = Const.prototype.toString = function () {
    return this.val.toString();
};
///
var varLib = ['x', 'y', 'z'];

function Variable(name) {
    this.index = varLib.indexOf(name);
}

Variable.prototype.evaluate = function () {
    return arguments[this.index];
};
Variable.prototype.prefix = Variable.prototype.toString = function () {
    return varLib[this.index];
};
///
Add.prototype = Object.create(Operation.prototype);

function Add(first, second) {
    Operation.call(this, first, second);
    this.operation = '+';
}

Add.prototype.constructor = Add;
Add.prototype.doOperation = function (first, second) {
    return first + second;
};
///
Subtract.prototype = Object.create(Operation.prototype);

function Subtract(first, second) {
    Operation.call(this, first, second);
    this.operation = '-';
}

Subtract.prototype.constructor = Subtract;
Subtract.prototype.doOperation = function (first, second) {
    return first - second;
};
///
Divide.prototype = Object.create(Operation.prototype);

function Divide(first, second) {
    Operation.call(this, first, second);
    this.operation = '/';
}

Divide.prototype.constructor = Divide;
Divide.prototype.doOperation = function (first, second) {
    return first / second;
};
///
Multiply.prototype = Object.create(Operation.prototype);

function Multiply(first, second) {
    Operation.call(this, first, second);
    this.operation = '*';
}

Multiply.prototype.constructor = Multiply;
Multiply.prototype.doOperation = function (first, second) {
    return first * second;
};
///
Negate.prototype = Object.create(UnaryOperation.prototype);

function Negate(first) {
    Operation.call(this, first);
    this.operation = 'negate';
}

Negate.prototype.constructor = Negate;
Negate.prototype.doOperation = function (first) {
    return -first;
};
///
Square.prototype = Object.create(UnaryOperation.prototype);

function Square(first) {
    Operation.call(this, first);
    this.operation = 'square';
}

Square.prototype.constructor = Square;
Square.prototype.doOperation = function (first) {
    return first * first;
};
///
Sqrt.prototype = Object.create(UnaryOperation.prototype);

function Sqrt(first) {
    Operation.call(this, first);
    this.operation = 'sqrt';
}

Sqrt.prototype.constructor = Sqrt;
Sqrt.prototype.doOperation = function (first) {
    return Math.sqrt(Math.abs(first));
};
//
ArcTan.prototype = Object.create(UnaryOperation.prototype);

function ArcTan(first) {
    Operation.call(this, first);
    this.operation = 'atan';
}

ArcTan.prototype.constructor = ArcTan;
ArcTan.prototype.doOperation = function (first) {
    return Math.atan(first);
};
//
Exp.prototype = Object.create(UnaryOperation.prototype);

function Exp(first) {
    Operation.call(this, first);
    this.operation = 'exp';
}

Exp.prototype.constructor = Exp;
Exp.prototype.doOperation = function (first) {
    return Math.exp(first);
};

//
function ParserException(message, ex, i) {
    this.message = message + "\n" + ex + '\n';
    for (var k = 0; k < i; k++) {
        this.message += " ";
    }
    this.message += "^\n";
}

ParserException.prototype = Object.create(Error.prototype);
ParserException.prototype.name = "ParserException";
ParserException.prototype.constructor = ParserException;

///
function isDigit(chr) {
    return chr >= '0' && chr <= '9';
}

function isMemberOfVars(chr) {
    return chr !== "(" && chr !== ")" && chr !== " ";
}

function isNumber(ex, i) {
    return (isDigit(ex[i]) || ex[i] === '-' && i + 1 < ex.length && isDigit(ex[i + 1]));
}

function skipWhitespace(ex, i) {
    while (i !== ex.length && ex[i] === ' ') {
        i++;
    }
    return i;
}

var OPERATIONS = {
    '+': [Add, 2],
    '-': [Subtract, 2],
    '*': [Multiply, 2],
    '/': [Divide, 2],
    'negate': [Negate, 1],
    'exp': [Exp, 1],
    'atan': [ArcTan, 1]
};

function isCurOperation(line, checkingOp, startPos) {
    if (startPos + checkingOp.length > line.length) {
        return false;
    }
    for (var i = 0; i < checkingOp.length; i++) {
        if (line[startPos + i] !== checkingOp[i]) {
            return false;
        }
    }
    return true;
}

function getNexToken(ex, i) {
    var beginInd = i;
    //print("|" + ex[0] + " " + ex[1] + "|");
    if (isNumber(ex, i)) {
        i++;
        while ((i < ex.length && isDigit(ex[i]))) {
            i++
        }
        return [ex.substring(beginInd, i), i];
    }
    if (ex[i] === '(' || ex[i] === ')') {
        // print(i + " find" + ex[i]);
        return [ex[i], i + 1];
    }
    //
    for (var op in OPERATIONS) {
        //print(op+" "+ex[i]+"a");
        if (isCurOperation(ex, op, i)) {
            i += op.length;
            //print(op+"\e");
            return [op, i];
        }
    }
    //print(op+"/z");
    //
    while (isMemberOfVars(ex[i]) && i < ex.length) {
        i++;
    }
    var maybeVar = ex.substring(beginInd, i);
    if (varLib.indexOf(maybeVar) !== -1) {
        return [maybeVar, i];
    }
    //
    while (i < ex.length && ex[i] !== ' ') {
        i++;
    }
    throw new ParserException("Unsupported Operation:" + ex.substring(beginInd, i) + " ", ex, beginInd);
}

function createObject(objType, args) {
    var object = Object.create(objType.prototype);
    var constr = objType.apply(object, args);
    if (constr === undefined) {
        return object;
    }
    return constr;
}

function parsePrefix(ex) {
    var i = 0;
    var stack = [];
    var balance = 0;
    var startTokenInd = 0;
    ex = ex.trim();
    if (ex === "") {
        throw  new ParserException("Empty expression", null, 0);
    }
    while (i !== ex.length) {
        i = skipWhitespace(ex, i);
        startTokenInd = i;
        var FullToken = getNexToken(ex, i);
        var token = FullToken[0];
        i = FullToken[1];
        //print("Cur_token:" + token + "\\n");
        if (token === ')') {
            --balance;
            //print("balance=" + balance+"\n");
            if (balance < 0) {
                throw new ParserException("Closing bracket expected", ex, i);
            }
            if (stack[stack.length - 1][0] === '(') {
                throw new ParserException("Expression have empty brackets", ex, i)
            }
            //
            var curArgs = [];
            while (!(stack[stack.length - 1] in OPERATIONS || stack[stack.length - 1] === '(')) {
                curArgs.push(stack.pop());
            }
            var curOperation = stack.pop();
            curArgs.reverse();
            if (!(curOperation in OPERATIONS)) {
                //print(op[0]+"/n");
                throw new ParserException("Expected operation after '('", ex, (stack.length !== 0 ? i : 0));
            }
            var predictedBrace = stack.pop();
            if (predictedBrace[0] !== "(") {
                throw new ParserException("Expected opening bracket before '" + curOperation + "' operation", null, i);
            }
            //
            token = curOperation;
            if (curArgs.length > OPERATIONS[token][1] || curArgs.length < OPERATIONS[token][1]) {
                throw new ParserException("Invalid amount of arguments in operation '" + token + "'", ex, i);
            }
            stack.push(createObject(OPERATIONS[token][0], curArgs));
        } else if (token === '(') {
            balance++;
            stack.push(token); //
        } else if (token in OPERATIONS) {
            stack.push(token);
        } else if (varLib.indexOf(token) !== -1) {
            stack.push(new Variable(token));
        } else if (isNumber(token, 0)) {
            stack.push(new Const(parseInt(token)));
        }
    }
    if (balance !== 0) {
        throw new ParserException("Missing brace detected in ", ex, i);
    }
    if (stack.length !== 1) {
        throw new ParserException("Invalid amount of arguments", null, 0);
    }
    return stack[0];

};

