package sepm.ss2017.e1625772.service;

import sepm.ss2017.e1625772.domain.BoxStatistics;

import java.time.LocalDate;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public interface BoxStatisticsService {
    BoxStatistics getStatistics(LocalDate beginTime, LocalDate endTime);
}
