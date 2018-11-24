package expression.exceptions;

public class DivideByZero extends Exception {
    public DivideByZero() {
        super("You try to divide by zero!");
    }
}
