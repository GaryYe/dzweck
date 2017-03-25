package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Implementations of this data access object interface should provide the necessary actions to access/modify the
 * data in their way.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BookingDAO {
    /**
     * Finds one booking with the given id
     *
     * @param id the id of the queried booking
     * @return the id of the booking or null if not found
     * @throws DataAccessException if an exception occurred while accessing the data
     */
    Booking findOne(Long id);

    /**
     * Finds all bookings that are available in the system.
     *
     * @return all bookings
     * @throws DataAccessException if an exception occurred while accessing the data
     */
    Collection<Booking> findAll();

    /**
     * Finds all bookings that intersect with the given time frame. Two time frames intersect if they have one common
     * day.
     *
     * @param begin the begin time
     * @param end   the end time
     * @throws DataAccessException if an exception occurred while accessing the data
     */
    Collection<Booking> findAllIntersecting(LocalDate begin, LocalDate end);

    /**
     * Creates the given booking.
     *
     * @param booking the booking to create
     * @throws DataAccessException if an exception occurred while accessing the data
     */
    void create(Booking booking);

    /**
     * Deletes the given booking
     *
     * @param booking the booking to delete
     * @throws DataAccessException if an exception occurred while accessing the data
     */
    void delete(Booking booking);

    /**
     * Updates the given booking with the new booking.
     *
     * @param booking the booking to update
     * @throws DataAccessException if an exception occurred while accessing the data
     */
    void update(Booking booking);
}
