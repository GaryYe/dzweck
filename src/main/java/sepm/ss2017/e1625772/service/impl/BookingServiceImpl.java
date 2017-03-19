package sepm.ss2017.e1625772.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.service.BookingService;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingDAO bookingDAO;

    @Autowired
    public BookingServiceImpl(BookingDAO bookingDAO) {
        this.bookingDAO = bookingDAO;
    }

    @Override
    public Collection<Booking> findAll() throws BusinessLogicException {
        try {
            return bookingDAO.findAll();
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public Collection<Booking> findAllBetween(LocalDate begin, LocalDate end) throws BusinessLogicException {
        try {
            return bookingDAO.findAllBetween(begin, end);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public void create(Booking booking) throws BusinessLogicException {
        try {
            bookingDAO.create(booking);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public void delete(Booking booking) throws BusinessLogicException {
        try {
            bookingDAO.delete(booking);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }
}
