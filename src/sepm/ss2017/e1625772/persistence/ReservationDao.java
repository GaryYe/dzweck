package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Reservation;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Created by gary on 05.03.17.
 */
public interface ReservationDao {
    Collection<Reservation> findAll();
    Collection<Reservation> findAllBetween(LocalDateTime begin, LocalDateTime end);
    Reservation create(Reservation reservation);
}
