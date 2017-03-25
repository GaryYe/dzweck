package sepm.ss2017.e1625772.utils;

import org.junit.Test;

import static java.time.LocalDate.of;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class DateUtilsTest {
    @Test
    public void testJustTwoDays() {
        int[] ret = DateUtils.countWeekdays(of(2017, 3, 14), of(2017, 3, 15));
        int[] exp = {0, 1, 1, 0, 0, 0, 0};
        assertArrayEquals(exp, ret);
    }

    @Test
    public void testAWholeWeekBetween() {
        int[] ret = DateUtils.countWeekdays(of(2017, 3, 14), of(2017, 3, 15 + 7));
        int[] exp = {1, 2, 2, 1, 1, 1, 1};
        assertArrayEquals(exp, ret);
    }

    @Test
    public void testMondayToSunday() {
        int[] ret = DateUtils.countWeekdays(of(2017, 3, 13), of(2017, 3, 19));
        int[] exp = {1, 1, 1, 1, 1, 1, 1};
        assertArrayEquals(exp, ret);
    }

    @Test
    public void testBetweenOneDay() {
        assertEquals(1, DateUtils.numberOfDaysBetween(of(2017, 3, 15), of(2017, 3, 15)));
    }

    @Test
    public void testBetweenMultipleDay() {
        assertEquals(8, DateUtils.numberOfDaysBetween(of(2017, 3, 15), of(2017, 3, 15 + 7)));
    }
}