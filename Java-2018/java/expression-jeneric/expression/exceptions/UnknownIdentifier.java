package expression.exceptions;


public class UnknownIdentifier extends ParsingException {
    public UnknownIdentifier(String expression, StringBuilder out, int position) {
        super("Identifier which is started on " + position + "(th/st/nd) position is NOT supported" + "\n" +
                expression + "\n"
                + getLocation(position));
    }
}