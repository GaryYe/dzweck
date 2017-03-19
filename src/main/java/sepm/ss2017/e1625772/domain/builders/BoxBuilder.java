package sepm.ss2017.e1625772.domain.builders;

import sepm.ss2017.e1625772.domain.Box;

import java.awt.*;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class BoxBuilder {
    private Box built;

    public BoxBuilder(Long id) {
        this.built = new Box();
        this.built.setId(id);
    }

    public BoxBuilder name(String name) {
        this.built.setName(name);
        return this;
    }

    public BoxBuilder area(double area) {
        this.built.setArea(area);
        return this;
    }

    public BoxBuilder dailyRate(double dailyRate) {
        this.built.setDailyRate(dailyRate);
        return this;
    }

    public BoxBuilder windows(boolean hasWindows) {
        this.built.setHasWindows(hasWindows);
        return this;
    }

    public BoxBuilder indoor(boolean isIndoor) {
        this.built.setIndoor(isIndoor);
        return this;
    }

    public BoxBuilder image(Image image) {
        this.built.setImage(image);
        return this;
    }

    public Box create() {
        return built;
    }
}

