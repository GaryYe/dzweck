package sepm.ss2017.e1625772.utils;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class DateUtils {
    /**
     * @param begin
     * @param end
     * @return
     */
    public static int[] countWeekdays(LocalDate begin, LocalDate end) {
        int[] count = new int[7];

        while (begin.getDayOfWeek().getValue() != 1 && !begin.isAfter(end)) {
            count[begin.getDayOfWeek().getValue() - 1]++;
            begin = begin.plusDays(1);
        }

        if (begin.isAfter(end))
            return count;

        while (end.getDayOfWeek().getValue() != 7) {
            count[end.getDayOfWeek().getValue() - 1]++;
            end = end.minusDays(1);
        }

        int n = numberOfDaysBetween(begin, end);
        assert (n % 7 == 0);
        for (int i = 0; i < 7; i++)
            count[i] += n / 7;
        return count;
    }

    public static int numberOfDaysBetween(LocalDate begin, LocalDate end) {
        return (int) (DAYS.between(begin, end) + 1);
    }

    public static LocalDate max(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2) ? date1 : date2;
    }

    public static LocalDate min(LocalDate date1, LocalDate date2) {
        return date2.isAfter(date1) ? date1 : date2;
    }
}
