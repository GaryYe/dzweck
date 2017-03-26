package sepm.ss2017.e1625772.service.impl;

import org.junit.Before;
import org.junit.Test;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.Receipt;
import sepm.ss2017.e1625772.domain.builders.BookingBuilder;
import sepm.ss2017.e1625772.domain.builders.BoxBookingBuilder;
import sepm.ss2017.e1625772.domain.builders.BoxBuilder;
import sepm.ss2017.e1625772.exceptions.ObjectNotFoundException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.service.ReceiptService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalDate.of;
import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class ReceiptServiceImplTest {
    private BoxBookingDAO boxBookingDAO;
    private BookingDAO bookingDAO;
    private BoxDAO boxDAO;
    private ReceiptService receiptService;

    @Before
    public void setUp() {
        boxDAO = mock(BoxDAO.class);
        bookingDAO = mock(BookingDAO.class);
        boxBookingDAO = mock(BoxBookingDAO.class);
        receiptService = new ReceiptServiceImpl(boxBookingDAO, bookingDAO, boxDAO);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void testObjectNotFoundException() throws ObjectNotFoundException {
        Long BOOKING_ID = 1L;
        when(bookingDAO.findOne(1L)).thenReturn(null);
        receiptService.calculateReceipt(1L);
    }

    @Test
    public void testFunctionality() throws ObjectNotFoundException {
        LocalDate beginTime = of(2017, 2, 15);
        LocalDate endTime = of(2017, 2, 20);
        when(bookingDAO.findOne(1L)).thenReturn(new BookingBuilder(1L)
                .beginTime(beginTime)
                .endTime(endTime)
                .create());
        when(boxBookingDAO.findAllByBooking(1L)).thenReturn(Arrays.asList(
                new BoxBookingBuilder(1L, 1L).agreedDailyRate(20.0).create(),
                new BoxBookingBuilder(1L, 2L).agreedDailyRate(30.0).create()
        ));
        Box box1 = new BoxBuilder(1L).create();
        Box box2 = new BoxBuilder(2L).create();

        when(boxDAO.findOne(1L)).thenReturn(box1);
        when(boxDAO.findOne(2L)).thenReturn(box2);

        Map<Box, Double> pricePerBox = new HashMap<>();
        pricePerBox.put(box1, 20.0 * 6);
        pricePerBox.put(box2, 30.0 * 6);

        Receipt receipt = new Receipt();
        receipt.setNumberOfDays(6);
        receipt.setBeginTime(beginTime);
        receipt.setEndTime(endTime);
        receipt.setTotalPrice(20 * 6.0 + 30 * 6.0);
        receipt.setPricePerBox(pricePerBox);
        Receipt retrieved = receiptService.calculateReceipt(1L);
        assertEquals(receipt, retrieved);
    }

}