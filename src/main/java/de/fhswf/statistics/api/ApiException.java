package de.fhswf.statistics.api;

public class ApiException extends Exception {

    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }

    public ApiException(String formattedMessage, Object... args) {
        super(String.format(formattedMessage, args));
    }

    public ApiException(String formattedMessage, Throwable cause, Object... args) {
        super(String.format(formattedMessage, args), cause);
    }
}
