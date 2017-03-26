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
    /**
     * Finds all box bookings that are in the underlying system.
     *
     * @return all box bookings in a list
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    List<BoxBooking> findAll();

    /**
     * Finds all box bookings that are in the underlying system, which correspond to the given booking id.
     *
     * @param bookingId the id to check against
     * @return all box bookings in a list
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    List<BoxBooking> findAllByBooking(Long bookingId);

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
     * @throws ServiceException             if an error occurred while the service was processing the request
     */
    void create(BoxBooking boxBooking) throws BoxBookingCollisionException, ObjectNotFoundException,
            DuplicatedObjectException;

    /**
     * Deletes the given box booking.
     *
     * @param boxBooking the box booking to delete
     * @throws ObjectNotFoundException if the box booking was not found
     * @throws ServiceException        if an error occurred while the service was processing the request
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

    /**
     * Returns the box bookings that are in conflict with the given booking and the bookings in question.
     *
     * @param booking           the booking to check against
     * @param bookingInQuestion the bookings that are in question
     * @return the conflicting box bookings in a list
     * @throws ServiceException if an error occurred while the service was operating
     */
    List<BoxBooking> conflictingBoxBookings(Booking booking, List<BoxBooking> bookingInQuestion);

    /**
     * Returns the box bookings that are in conflict with the given booking and the bookings in question.
     *
     * @param boxBooking the boxBooking to check against
     * @return the conflicting box bookings in a list
     * @throws ServiceException if an error occurred while the service was operating
     */
    List<BoxBooking> conflictingBoxBookings(BoxBooking boxBooking);
}
