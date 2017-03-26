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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Receipt receipt = (Receipt) o;

        if (numberOfDays != receipt.numberOfDays) return false;
        if (beginTime != null ? !beginTime.equals(receipt.beginTime) : receipt.beginTime != null) return false;
        if (endTime != null ? !endTime.equals(receipt.endTime) : receipt.endTime != null) return false;
        if (pricePerBox != null ? !pricePerBox.equals(receipt.pricePerBox) : receipt.pricePerBox != null) return false;
        return totalPrice != null ? totalPrice.equals(receipt.totalPrice) : receipt.totalPrice == null;
    }

    @Override
    public int hashCode() {
        int result = beginTime != null ? beginTime.hashCode() : 0;
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (pricePerBox != null ? pricePerBox.hashCode() : 0);
        result = 31 * result + (totalPrice != null ? totalPrice.hashCode() : 0);
        result = 31 * result + numberOfDays;
        return result;
    }
}
