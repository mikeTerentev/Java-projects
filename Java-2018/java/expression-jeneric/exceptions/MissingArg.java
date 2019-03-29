package expression.exceptions;


public class MissingArg extends ParsingException {
    public MissingArg(String expression, int position) {
        super("wrong expression  detected in " + position + "th position" + "\n" +
                expression + "\n" +
                getLocation(position));
    }
}