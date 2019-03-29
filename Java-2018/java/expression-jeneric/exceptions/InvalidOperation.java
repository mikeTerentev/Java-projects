package expression.exceptions;

public class InvalidOperation extends EvaluatingException {
    public InvalidOperation(final String message) {
        super("Illegar operation:" + message);
    }
}
