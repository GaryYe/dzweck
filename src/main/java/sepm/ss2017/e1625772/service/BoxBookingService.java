package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxBookingService {
    List<BoxBooking> findAll() throws BusinessLogicException;

    List<BoxBooking> findAllByBooking(Long bookingId) throws BusinessLogicException;

    List<BoxBooking> findAllByBox(Long boxId) throws BusinessLogicException;

    void create(BoxBooking boxBooking) throws BusinessLogicException;

    void delete(BoxBooking boxBooking) throws BusinessLogicException;
}
