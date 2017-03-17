package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface ReservationDAO {
    Collection<Reservation> findAll();
    Collection<Reservation> findAllBetween(LocalDateTime begin, LocalDateTime end);
    Reservation create(Reservation reservation);
}
