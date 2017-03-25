package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxStatistics;

import java.time.DayOfWeek;
import java.time.LocalDate;

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
     */
    BoxStatistics getStatistics(LocalDate beginTime, LocalDate endTime);


    /**
     * Finds the box with the least/most number of bookings in the given time span.
     *
     * @param begin the begin time
     * @param end   the end time
     * @param best  true for the best or the worst
     * @return an outlier - in case there are many boxes that match the criteria then the one with the lowest id will
     * be chosen.
     */
    Box findOutlierBox(LocalDate begin, LocalDate end, boolean best);

    /**
     * Finds the box with the least/most number of bookings on the given day of the week.
     *
     * @param dayOfWeek the day of the week
     * @param best      true for the best or the worst
     * @return an outlier - in case there are many boxes that match the criteria then the one with the lowest id will
     * be chosen.
     */
    Box findOutlierBox(DayOfWeek dayOfWeek, boolean best);
}
