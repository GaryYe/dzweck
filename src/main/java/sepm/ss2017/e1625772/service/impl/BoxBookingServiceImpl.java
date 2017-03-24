package sepm.ss2017.e1625772.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.ObjectNotFoundException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.service.BoxBookingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
    public List<BoxBooking> findAllByBox(Long boxId) {
        try {
            return boxBookingDAO.findAllByBox(boxId);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void create(BoxBooking boxBooking) {
        try {
            boxBookingDAO.create(boxBooking);
            LOG.info("BoxBooking between boxId={} and bookingId={} was created", boxBooking.getBoxId(), boxBooking
                    .getBookingId());
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

    /**
     * {@inheritDoc}
     * <p>
     * This implementation has an asymptotically O(n) run time, where n is the number of boxes.
     */
    @Override
    public List<BoxBooking> conflictingBoxBookings(Booking booking) {
        if (booking == null)
            throw new IllegalArgumentException("Booking can not be null");
        try {
            Set<Long> boxIdOurs = new TreeSet<>();
            for (BoxBooking boxBooking : boxBookingDAO.findAllByBooking(booking.getId()))
                boxIdOurs.add(boxBooking.getBoxId());
            List<Booking> intersectingBookings = new ArrayList<>(bookingDAO.findAllBetween(booking.getBeginTime(),
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
}
