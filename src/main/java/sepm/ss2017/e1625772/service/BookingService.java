package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BookingService {
    LocalDate MIN = LocalDate.of(1970, 1, 1);
    LocalDate MAX = LocalDate.of(2038, 1, 1);

    List<Booking> findAll() throws BusinessLogicException;

    List<Booking> findAllBetween(LocalDate begin, LocalDate end) throws BusinessLogicException;

    void create(Booking booking) throws BusinessLogicException;

    void delete(Booking booking) throws BusinessLogicException;

    Booking findOne(Long id) throws BusinessLogicException;
}
