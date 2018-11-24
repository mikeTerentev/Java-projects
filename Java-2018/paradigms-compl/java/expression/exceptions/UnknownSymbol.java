package expression.exceptions;

public class UnknownSymbol extends ParsingException {
    public UnknownSymbol(String expression, String message, int pos) {
        super("Unknown symbol " + message + "(th/st/nd) position" + "\n" +
                expression + "\n" +
                getLocation(pos));
    }
}
