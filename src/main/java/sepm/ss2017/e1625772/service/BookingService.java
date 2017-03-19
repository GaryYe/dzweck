package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BookingService {
    List<Booking> findAll() throws BusinessLogicException;

    List<Booking> findAllBetween(LocalDate begin, LocalDate end) throws BusinessLogicException;

    void create(Booking booking) throws BusinessLogicException;

    void delete(Booking booking) throws BusinessLogicException;
}
