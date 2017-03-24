package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.ServiceException;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxBookingService {
    List<BoxBooking> findAll() throws ServiceException;

    List<BoxBooking> findAllByBooking(Long bookingId) throws ServiceException;

    List<BoxBooking> findAllByBox(Long boxId) throws ServiceException;

    void create(BoxBooking boxBooking) throws ServiceException;

    void delete(BoxBooking boxBooking);

    /**
     * Returns the box bookings that are in conflict with the given booking.
     *
     * @param booking the booking to check against
     * @return the conflicting box bookings in a list
     * @throws ServiceException if an error occurred while the service was operating
     */
    List<BoxBooking> conflictingBoxBookings(Booking booking);
}
