package sepm.ss2017.e1625772.exceptions;

import sepm.ss2017.e1625772.domain.BoxBooking;

import java.util.List;

/**
 * This exception should indicate which box and bookings are in conflict with the given request.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
public class BoxBookingCollisionException extends Exception {
    private final List<BoxBooking> conflicting;

    public BoxBookingCollisionException(List<BoxBooking> conflicting) {
        this.conflicting = conflicting;
    }

    public List<BoxBooking> getConflicting() {
        return conflicting;
    }

    @Override
    public String getMessage() {
        StringBuilder sb = new StringBuilder("Following box bookings are in conflict with the given one:");
        for (BoxBooking boxBooking : conflicting)
            sb.append(boxBooking);
        return sb.toString();
    }
}
