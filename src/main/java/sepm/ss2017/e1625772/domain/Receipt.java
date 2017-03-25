package sepm.ss2017.e1625772.domain;

import java.time.LocalDate;
import java.util.Map;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class Receipt {
    private LocalDate beginTime;
    private LocalDate endTime;
    private Map<Box, Double> pricePerBox;
    private Double totalPrice;
    private int numberOfDays;

    public LocalDate getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDate beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setNumberOfDays(int numberOfDays) {
        this.numberOfDays = numberOfDays;
    }

    public Map<Box, Double> getPricePerBox() {
        return pricePerBox;
    }

    public void setPricePerBox(Map<Box, Double> pricePerBox) {
        this.pricePerBox = pricePerBox;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
