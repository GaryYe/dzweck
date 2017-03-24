package sepm.ss2017.e1625772.exceptions;

/**
 *
 */
public class FormParsingException extends PresentationException {
    public FormParsingException() {
    }

    public FormParsingException(String message) {
        super(message);
    }

    public FormParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FormParsingException(Throwable cause) {
        super(cause);
    }

    public FormParsingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
