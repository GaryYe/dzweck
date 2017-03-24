package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxBookingDAO {
    /**
     * @return all box bookings in the system.
     * @throws DataAccessException if an error occurred while accessing the data
     */
    List<BoxBooking> findAll();

    /**
     * Finds all box bookings that have the given booking id.
     *
     * @param bookingId the id of the book
     * @return all box bookings that are related to the given booking id
     * @throws DataAccessException if an error occurred while accessing the data
     */
    List<BoxBooking> findAllByBooking(Long bookingId);

    /**
     * Finds all box bookings that have the given box id.
     *
     * @param boxId the id of the box
     * @return all box bookings that are related to the given box id
     * @throws DataAccessException if an error occurred while accessing the data
     */
    List<BoxBooking> findAllByBox(Long boxId);

    /**
     * Creates the given box booking relationship.
     *
     * @param boxBooking the box booking to create
     * @throws DataAccessException if an error occurred while accessing the data
     */
    void create(BoxBooking boxBooking);

    /**
     * @param boxBooking the box booking to delete
     * @throws DataAccessException if an error occurred while accessing the data
     */
    void delete(BoxBooking boxBooking);
}
