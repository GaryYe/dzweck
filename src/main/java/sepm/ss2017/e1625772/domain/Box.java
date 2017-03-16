package sepm.ss2017.e1625772.domain;

import java.awt.*;

/**
 *
 */
public class Box {
    private Long id;
    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public boolean hasWindows() {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static class BoxBuilder {
        private Box built;

        public BoxBuilder(Long id) {
            this.built = new Box();
            this.built.setId(id);
        }

        public BoxBuilder name(String name) {
            this.built.name = name;
            return this;
        }

        public BoxBuilder area(double area) {
            this.built.area = area;
            return this;
        }

        public BoxBuilder dailyRate(double dailyRate) {
            this.built.area = dailyRate;
            return this;
        }

        public BoxBuilder windows(boolean has) {
            this.built.hasWindows = has;
            return this;
        }

        public BoxBuilder indoor(boolean is) {
            this.built.isIndoor = is;
            return this;
        }

        public BoxBuilder image(Image image) {
            this.built.image = image;
            return this;
        }

        public Box create() {
            return built;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Box box = (Box) o;

        if (Double.compare(box.area, area) != 0) return false;
        if (Double.compare(box.dailyRate, dailyRate) != 0) return false;
        if (hasWindows != box.hasWindows) return false;
        if (isIndoor != box.isIndoor) return false;
        if (id != null ? !id.equals(box.id) : box.id != null) return false;
        if (name != null ? !name.equals(box.name) : box.name != null) return false;
        return image != null ? image.equals(box.image) : box.image == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        temp = Double.doubleToLongBits(area);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(dailyRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (hasWindows ? 1 : 0);
        result = 31 * result + (isIndoor ? 1 : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }
}
