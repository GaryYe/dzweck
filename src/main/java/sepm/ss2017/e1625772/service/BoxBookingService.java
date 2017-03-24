package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.ObjectNotFoundException;
import sepm.ss2017.e1625772.exceptions.ServiceException;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxBookingService {
    List<BoxBooking> findAll();

    List<BoxBooking> findAllByBooking(Long bookingId);

    List<BoxBooking> findAllByBox(Long boxId);

    /**
     * @param boxBooking the box booking relationship to create
     *                   TODO: throws Box does not exist?
     *                   TODO: booking does not exist?
     *                   TODO:
     */
    void create(BoxBooking boxBooking);

    /**
     * Deletes the given box booking.
     *
     * @param boxBooking the box booking to delete
     * @throws ObjectNotFoundException if the box booking was not found
     */
    void delete(BoxBooking boxBooking) throws ObjectNotFoundException;

    /**
     * Returns the box bookings that are in conflict with the given booking.
     *
     * @param booking the booking to check against
     * @return the conflicting box bookings in a list
     * @throws ServiceException if an error occurred while the service was operating
     */
    List<BoxBooking> conflictingBoxBookings(Booking booking);
}
