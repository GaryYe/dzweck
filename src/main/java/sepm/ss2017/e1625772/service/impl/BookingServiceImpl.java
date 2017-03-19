package sepm.ss2017.e1625772.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.service.BookingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
    public List<Booking> findAll() throws BusinessLogicException {
        try {
            return new ArrayList<>(bookingDAO.findAll());
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public List<Booking> findAllBetween(LocalDate begin, LocalDate end) throws BusinessLogicException {
        if (begin == null || end == null)
            throw new IllegalArgumentException("Begin and end can not be null");
        if (begin.isAfter(end))
            throw new IllegalArgumentException("Begin can not be after end");
        try {
            return new ArrayList<>(bookingDAO.findAllBetween(begin, end));
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
