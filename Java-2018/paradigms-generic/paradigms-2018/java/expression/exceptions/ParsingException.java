package expression.exceptions;

public class ParsingException extends Exception {

    public ParsingException(String message) {
        super(message);
    }


    static protected String getLocation(int ind) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < ind; i++) {
            line.append(' ');
        }
        line.append('^');
        return line.toString();
    }
}

