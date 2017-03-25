package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.BoxBookingCollisionException;
import sepm.ss2017.e1625772.exceptions.DuplicatedObjectException;
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
     * Creates the given box booking, but also performs a check whether there are any conflicts between the given one
     * and the already existing ones.
     *
     * @param boxBooking the box booking relationship to create
     * @throws IllegalArgumentException     if the given box booking is null
     * @throws ObjectNotFoundException      if the booking or the box do not exist
     * @throws BoxBookingCollisionException if by creating the box booking there will be a conflict with other box
     *                                      bookings
     * @throws DuplicatedObjectException    if the given box booking already exists
     */
    void create(BoxBooking boxBooking) throws BoxBookingCollisionException, ObjectNotFoundException,
            DuplicatedObjectException;

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

    List<BoxBooking> conflictingBoxBookings(Booking booking, List<BoxBooking> bookingInQuestion);

    List<BoxBooking> conflictingBoxBookings(BoxBooking booking);
}
