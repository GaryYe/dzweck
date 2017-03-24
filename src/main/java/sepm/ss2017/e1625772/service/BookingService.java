package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BoxBookingCollisionException;

import java.time.LocalDate;
import java.util.List;

/**
 * The booking service is responsible for handling data requests regarding the bookings.
 *
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BookingService {
    LocalDate MIN = LocalDate.of(1970, 1, 1);
    LocalDate MAX = LocalDate.of(2038, 1, 1);

    List<Booking> findAll();

    List<Booking> findAllBetween(LocalDate begin, LocalDate end);

    void create(Booking booking);

    void delete(Booking booking);

    void update(Booking booking) throws BoxBookingCollisionException;

    Booking findOne(Long id);
}
