package sepm.ss2017.e1625772.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxBooking;
import sepm.ss2017.e1625772.domain.BoxStatistics;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BookingDAO;
import sepm.ss2017.e1625772.persistence.BoxBookingDAO;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.service.BookingService;
import sepm.ss2017.e1625772.service.BoxStatisticsService;
import sepm.ss2017.e1625772.utils.DateUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static sepm.ss2017.e1625772.utils.DateUtils.max;
import static sepm.ss2017.e1625772.utils.DateUtils.min;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BoxStatisticsServiceImpl implements BoxStatisticsService {
    private final BookingDAO bookingDAO;
    private final BoxBookingDAO boxBookingDAO;
    private final BoxDAO boxDAO;

    @Autowired
    public BoxStatisticsServiceImpl(BookingDAO bookingDAO, BoxBookingDAO boxBookingDAO, BoxDAO boxDAO) {
        this.bookingDAO = bookingDAO;
        this.boxBookingDAO = boxBookingDAO;
        this.boxDAO = boxDAO;
    }

    @Override
    public BoxStatistics getStatistics(LocalDate beginTime, LocalDate endTime) {
        if (beginTime == null)
            beginTime = BookingService.MIN;
        if (endTime == null)
            endTime = BookingService.MAX;

        try {
            List<Booking> intersecting = new ArrayList<>(bookingDAO.findAllIntersecting(beginTime, endTime));
            Map<Box, int[]> map = new HashMap<>();
            for (Booking booking : intersecting) {
                LocalDate interBegin = max(booking.getBeginTime(), beginTime);
                LocalDate interEnd = min(booking.getEndTime(), endTime);
                int[] arr = DateUtils.countWeekdays(interBegin, interEnd);
                List<BoxBooking> boxBookings = boxBookingDAO.findAllByBooking(booking.getId());
                for (BoxBooking boxBooking : boxBookings) {
                    Box box = boxDAO.findOne(boxBooking.getBoxId());
                    for (int i = 0; i < 7; i++)
                        map.computeIfAbsent(box, k -> new int[7])[i] += arr[i];
                }
            }
            return new BoxStatistics(map);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Box findOutlierBox(LocalDate begin, LocalDate end, boolean best) {
        BoxStatistics boxStatistics = getStatistics(begin, end);
        Box bestBox = null;
        for (Box box : boxStatistics.boxes()) {
            if (bestBox == null ||
                    (best && boxStatistics.numberOfReservations(box) > boxStatistics.numberOfReservations(bestBox)) ||
                    (!best && boxStatistics.numberOfReservations(box) < boxStatistics.numberOfReservations(bestBox))) {
                bestBox = box;
            }
        }
        return bestBox;
    }

    @Override
    public Box findOutlierBox(DayOfWeek dayOfWeek, boolean best) {
        BoxStatistics boxStatistics = getStatistics(null, null);
        Box bestBox = null;
        for (Box box : boxStatistics.boxes()) {
            if (bestBox == null ||
                    (best && boxStatistics.numberOfReservations(box, dayOfWeek) > boxStatistics.numberOfReservations
                            (bestBox, dayOfWeek)) ||
                    (!best && boxStatistics.numberOfReservations(box, dayOfWeek) < boxStatistics.numberOfReservations
                            (bestBox, dayOfWeek))) {
                bestBox = box;
            }
        }
        return bestBox;
    }
}
