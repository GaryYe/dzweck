package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Booking;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface ReservationDAO {
    Collection<Booking> findAll();
    Collection<Booking> findAllBetween(LocalDateTime begin, LocalDateTime end);
    Booking create(Booking booking);
}
