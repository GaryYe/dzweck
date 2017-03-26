package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxStatistics;
import sepm.ss2017.e1625772.exceptions.ServiceException;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxStatisticsService {
    /**
     * Retrieves the statistics in the given time frame.
     *
     * @param beginTime the begin time
     * @param endTime   the end time (included)
     * @return a box statistics object which includes the number of bookings on each day of the week (Monday to Sunday).
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    BoxStatistics getStatistics(LocalDate beginTime, LocalDate endTime);


    /**
     * Finds the box with the least/most number of bookings in the given time span. Additionally, a list of
     * {@link DayOfWeek} must be provided. The results will be filtered according to this list.
     *
     * @param begin      the begin time
     * @param end        the end time
     * @param best       true for the best or the worst
     * @param dayOfWeeks a list of {@link DayOfWeek} that should specify the days the user is interested in.
     * @return an outlier - in case there are many boxes that match the criteria then only one will be taken (no
     * special order)
     * @throws ServiceException if an error occurred while the service was processing the request
     */
    Box findOutlierBox(LocalDate begin, LocalDate end, boolean best, List<DayOfWeek> dayOfWeeks);
}
