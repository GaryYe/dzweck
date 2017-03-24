package sepm.ss2017.e1625772.exceptions;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class DuplicatedObjectException extends Exception {
    public DuplicatedObjectException() {
    }

    public DuplicatedObjectException(String message) {
        super(message);
    }

    public DuplicatedObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicatedObjectException(Throwable cause) {
        super(cause);
    }

    public DuplicatedObjectException(String message, Throwable cause, boolean enableSuppression, boolean
            writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
