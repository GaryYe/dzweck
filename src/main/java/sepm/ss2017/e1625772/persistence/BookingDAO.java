package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BookingDAO {
    Collection<Booking> findAll() throws DataAccessException;

    Collection<Booking> findAllBetween(LocalDate begin, LocalDate end) throws DataAccessException;

    void create(Booking booking) throws DataAccessException;

    void delete(Booking booking) throws DataAccessException;
}
