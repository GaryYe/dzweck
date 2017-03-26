package sepm.ss2017.e1625772.service.impl;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.domain.Receipt;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.ObjectNotFoundException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.service.ReceiptService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger LOG = getLogger(ReceiptServiceImpl.class);

    private final BoxBookingDAO boxBookingDAO;
    private final BookingDAO bookingDAO;
    private final BoxDAO boxDAO;

    @Autowired
    public ReceiptServiceImpl(BoxBookingDAO boxBookingDAO, BookingDAO bookingDAO, BoxDAO boxDAO) {
        this.boxBookingDAO = boxBookingDAO;
        this.bookingDAO = bookingDAO;
        this.boxDAO = boxDAO;
    }

    @Override
    public Receipt calculateReceipt(Long bookingId) throws ObjectNotFoundException {
        try {
            LOG.debug("Calculating receipt of booking {}", bookingId);
            Receipt receipt = new Receipt();
            List<BoxBooking> relevant = boxBookingDAO.findAllByBooking(bookingId);
            Booking booking = bookingDAO.findOne(bookingId);

            if (booking == null)
                throw new ObjectNotFoundException();

            int numberOfDays = (int) DAYS.between(booking.getBeginTime(), booking.getEndTime()) + 1;
            receipt.setBeginTime(booking.getBeginTime());
            receipt.setEndTime(booking.getEndTime());
            receipt.setNumberOfDays(numberOfDays);

            Map<Box, Double> pricePerBox = new HashMap<>();

            Double totalPrice = 0.0;
            for (BoxBooking boxBooking : relevant) {
                Box box = boxDAO.findOne(boxBooking.getBoxId());
                Double price = numberOfDays * boxBooking.getAgreedDailyRate();
                totalPrice += price;
                pricePerBox.put(box, price);
            }
            receipt.setPricePerBox(pricePerBox);
            receipt.setTotalPrice(totalPrice);
            return receipt;
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

}
