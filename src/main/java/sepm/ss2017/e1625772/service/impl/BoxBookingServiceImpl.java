package sepm.ss2017.e1625772.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.service.BoxBookingService;

import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BoxBookingServiceImpl implements BoxBookingService {
    private final BoxBookingDAO boxBookingDAO;

    @Autowired
    public BoxBookingServiceImpl(BoxBookingDAO boxBookingDAO) {
        this.boxBookingDAO = boxBookingDAO;
    }

    @Override
    public List<BoxBooking> findAll() throws BusinessLogicException {
        try {
            return boxBookingDAO.findAll();
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public List<BoxBooking> findAllByBooking(Long bookingId) throws BusinessLogicException {
        try {
            return boxBookingDAO.findAllByBooking(bookingId);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public List<BoxBooking> findAllByBox(Long boxId) throws BusinessLogicException {
        try {
            return boxBookingDAO.findAllByBox(boxId);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public void create(BoxBooking boxBooking) throws BusinessLogicException {
        try {
            boxBookingDAO.create(boxBooking);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public void delete(BoxBooking boxBooking) throws BusinessLogicException {
        try {
            boxBookingDAO.delete(boxBooking);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }
}
