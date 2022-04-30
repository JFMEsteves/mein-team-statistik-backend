package de.fhswf.statistics.api.parser;

public class ParsingException extends Exception {

    public ParsingException() {
    }

    public ParsingException(String message) {
        super(message);
    }

    public ParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParsingException(String formattedMessage, Object... args) {
        super(String.format(formattedMessage, args));
    }

    public ParsingException(String formattedMessage, Throwable cause, Object... args) {
        super(String.format(formattedMessage, args), cause);
    }
}
