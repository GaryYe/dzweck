package sepm.ss2017.e1625772.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.BoxBookingCollisionException;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.service.BookingService;
import sepm.ss2017.e1625772.service.BoxBookingService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BookingServiceImpl implements BookingService {
    private final BookingDAO bookingDAO;
    private final BoxBookingService boxBookingService;

    @Autowired
    public BookingServiceImpl(BookingDAO bookingDAO, BoxBookingService boxBookingService) {
        this.bookingDAO = bookingDAO;
        this.boxBookingService = boxBookingService;
    }

    @Override
    public List<Booking> findAll() {
        try {
            return new ArrayList<>(bookingDAO.findAll());
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Booking> findAllIntersecting(LocalDate begin, LocalDate end) {
        if (begin == null || end == null)
            throw new IllegalArgumentException("Begin and end can not be null");
        if (begin.isAfter(end))
            throw new IllegalArgumentException("Begin can not be after end");
        try {
            return new ArrayList<>(bookingDAO.findAllIntersecting(begin, end));
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(Booking booking) {
        try {
            bookingDAO.create(booking);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(Booking booking) {
        try {
            bookingDAO.delete(booking);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Booking booking) throws BoxBookingCollisionException {
        try {
            checkForConflictingBoxBookings(booking);
            bookingDAO.update(booking);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Booking findOne(Long id) {
        try {
            return bookingDAO.findOne(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    private void checkForConflictingBoxBookings(Booking booking) throws BoxBookingCollisionException {
        List<BoxBooking> conflicting = boxBookingService.conflictingBoxBookings(booking);
        if (!conflicting.isEmpty())
            throw new BoxBookingCollisionException(conflicting);
    }
}
