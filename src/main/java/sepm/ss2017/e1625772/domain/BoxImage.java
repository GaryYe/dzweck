package sepm.ss2017.e1625772.domain;

import java.awt.image.BufferedImage;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
public class BoxImage {
    private Long boxId;
    private BufferedImage image;

    public BoxImage() {

    }

    public BoxImage(Long boxId, BufferedImage image) {
        this.boxId = boxId;
        this.image = image;
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
