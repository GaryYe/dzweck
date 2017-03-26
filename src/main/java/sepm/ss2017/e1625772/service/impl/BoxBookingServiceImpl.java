package sepm.ss2017.e1625772.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.*;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.service.BoxBookingService;

import java.util.*;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BoxBookingServiceImpl implements BoxBookingService {
    private static final Logger LOG = LoggerFactory.getLogger(BoxBookingServiceImpl.class);
    private final BoxBookingDAO boxBookingDAO;
    private final BookingDAO bookingDAO;

    @Autowired
    public BoxBookingServiceImpl(BoxBookingDAO boxBookingDAO, BookingDAO bookingDAO) {
        this.boxBookingDAO = boxBookingDAO;
        this.bookingDAO = bookingDAO;
    }

    @Override
    public List<BoxBooking> findAll() {
        try {
            return boxBookingDAO.findAll();
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BoxBooking> findAllByBooking(Long bookingId) {
        try {
            return boxBookingDAO.findAllByBooking(bookingId);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(BoxBooking boxBooking) throws DuplicatedObjectException, BoxBookingCollisionException,
            ObjectNotFoundException {
        try {
            List<BoxBooking> conflicting = conflictingBoxBookings(boxBooking);
            if (!conflicting.isEmpty())
                throw new BoxBookingCollisionException(conflicting);
            boxBookingDAO.create(boxBooking);
            LOG.info("BoxBooking ({}, {}) created", boxBooking.getBoxId(), boxBooking.getBookingId());
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void delete(BoxBooking boxBooking) throws ObjectNotFoundException {
        try {
            boxBookingDAO.delete(boxBooking);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BoxBooking> conflictingBoxBookings(Booking booking) {
        return conflictingBoxBookings(booking, boxBookingDAO.findAllByBooking(booking.getId()));
    }

    /**
     * {@inheritDoc}
     * <p>
     * This implementation has an asymptotically O(n) run time, where n is the number of boxes.
     */
    @Override
    public List<BoxBooking> conflictingBoxBookings(Booking booking, List<BoxBooking> bookingInQuestion) {
        if (booking == null)
            throw new IllegalArgumentException("Booking can not be null");
        try {
            Set<Long> boxIdOurs = new TreeSet<>();
            for (BoxBooking boxBooking : bookingInQuestion)
                boxIdOurs.add(boxBooking.getBoxId());
            List<Booking> intersectingBookings = new ArrayList<>(bookingDAO.findAllIntersecting(booking.getBeginTime(),
                    booking.getEndTime()));
            List<BoxBooking> conflicting = new ArrayList<>();
            for (Booking potentialConflictingBooking : intersectingBookings) {
                if (potentialConflictingBooking.getId().equals(booking.getId()))
                    continue;
                List<BoxBooking> boxBookingsOther = boxBookingDAO.findAllByBooking(potentialConflictingBooking.getId());
                for (BoxBooking boxBookingOther : boxBookingsOther) {
                    if (boxIdOurs.contains(boxBookingOther.getBoxId())) {
                        conflicting.add(boxBookingOther);
                    }
                }
            }
            return conflicting;
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<BoxBooking> conflictingBoxBookings(BoxBooking booking) {
        if (booking == null)
            throw new IllegalArgumentException("Booking can not be null");
        return conflictingBoxBookings(bookingDAO.findOne(booking.getBookingId()), Collections.singletonList(booking));
    }
}
