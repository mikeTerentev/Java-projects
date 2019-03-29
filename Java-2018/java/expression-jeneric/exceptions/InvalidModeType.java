package expression.exceptions;

public class InvalidModeType extends Exception {
    public InvalidModeType(String md) {
        super("Mode is negate supported: " + md);
    }
}
