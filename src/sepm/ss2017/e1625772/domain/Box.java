package sepm.ss2017.e1625772.domain;

import java.awt.*;

/**
 */
public class Box {
    private Long id;
    private double area;
    private double dailyRate;
    private boolean hasWindows;
    private boolean isIndoor;
    private Image image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isHasWindows() {
        return hasWindows;
    }

    public void setHasWindows(boolean hasWindows) {
        this.hasWindows = hasWindows;
    }

    public boolean isIndoor() {
        return isIndoor;
    }

    public void setIndoor(boolean indoor) {
        isIndoor = indoor;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
