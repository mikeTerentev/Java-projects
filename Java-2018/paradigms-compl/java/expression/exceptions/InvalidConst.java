package expression.exceptions;

public class InvalidConst extends ParsingException {
    public InvalidConst(String expression, int pos) {
        super("Can't work with this const value started on" + pos + "(th/st/nd) position" + "\n" +
                expression + "\n" +
                getLocation(pos));
    }
}
