"use strict";

// insert your code here
println("Hello", "world");

include("examples/0_1_magic.js");

// Lecture 1. Types and Functions
include("examples/1_1_types.js");
include("examples/1_2_functions.js");
include("examples/1_3_functions-hi.js");

// Lecture 2. Objects and Methods
include("examples/2_1_objects.js");
include("examples/2_2_closures.js");
include("examples/2_3_modules.js");
include("examples/2_4_stacks.js");
var cnst = function (num) {
    return function (x, y, z) {
        return num;
    }
};
var variable = function (name) {
    var tmp = name;
    return function (x, y, z) {
        if (tmp === "x") {
            return x;
        } else if (tmp === "y") {
            return y;
        } else if (tmp === "z") {
            return z;
        }
    }
};
var binaryOperation = function (operation) {
    return function (first, sec) {
        return function (x, y, z) {
            return operation(first(x, y, z), sec(x, y, z));
        }
    }
};
var unaryOperation = function (operation) {
    return function (first) {
        return function (x, y, z) {
            return operation(first(x, y, z));
        }
    }
};

var add = binaryOperation(function (a, b) {
    return a + b;
});
var subtract = binaryOperation(function (a, b) {
    return a - b;
});
var multiply = binaryOperation(function (a, b) {
    return a * b;
});
var divide = binaryOperation(function (a, b) {
    return a / b;
});
var negate = unaryOperation(function (a) {
    return -a;
});
var cube = unaryOperation(function (a) {
    return a * a * a;
});
var cuberoot = unaryOperation(function (a) {
    return Math.pow(a, 1 / 3);
});