package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxBookingDAO {
    List<BoxBooking> findAll() throws DataAccessException;

    List<BoxBooking> findAllByBooking(Long bookingId) throws DataAccessException;

    List<BoxBooking> findAllByBox(Long boxId) throws DataAccessException;

    void create(BoxBooking boxBooking) throws DataAccessException;

    void delete(BoxBooking boxBooking) throws DataAccessException;
}
