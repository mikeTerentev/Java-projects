package expression.exceptions;

public class Overflow extends EvaluatingException {
    public Overflow() {
        super("out of range");
    }
}
