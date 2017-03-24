package sepm.ss2017.e1625772.exceptions;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class PresentationException extends RuntimeException {
    public PresentationException() {
    }

    public PresentationException(String message) {
        super(message);
    }

    public PresentationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PresentationException(Throwable cause) {
        super(cause);
    }

    public PresentationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
