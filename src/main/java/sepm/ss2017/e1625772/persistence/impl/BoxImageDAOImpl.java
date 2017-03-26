package sepm.ss2017.e1625772.persistence.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import sepm.ss2017.e1625772.domain.BoxImage;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BoxImageDAO;

import javax.imageio.ImageIO;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Repository
public class BoxImageDAOImpl implements BoxImageDAO {
    private static final Logger LOG = LoggerFactory.getLogger(BoxImageDAOImpl.class);
    private final JdbcTemplate jdbcTemplate;
    private final String DIRECTORY = ".boximages/";

    @Autowired
    public BoxImageDAOImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BoxImage findByBox(Long boxId) {
        try {
            List<String> filePath = jdbcTemplate.query("SELECT FILE_PATH FROM BOXIMAGES WHERE BOX_ID = ?",
                    new Object[]{boxId}, (resultSet, i) -> {
                        return resultSet.getString(1);
                    });
            if (filePath.isEmpty())
                return null;
            File file = new File(filePath.get(0));
            return new BoxImage(boxId, ImageIO.read(file));
        } catch (DataAccessException | IOException e) {
            throw new sepm.ss2017.e1625772.exceptions.DataAccessException(e);
        }
    }

    private String filePathNaming(Long boxId) {
        return DIRECTORY + String.valueOf(boxId) + ".png";
    }


    private void checkDirectory() {
        if (new File(DIRECTORY).mkdir()) {
            LOG.info("Directory {} has been initialized", DIRECTORY);
        }
    }

    @Override
    public void create(BoxImage boxImage) {
        try {
            checkDirectory();
            File imageFile = new File(filePathNaming(boxImage.getBoxId()));
            ImageIO.write(boxImage.getImage(), "png", imageFile);
            jdbcTemplate.update("INSERT INTO BOXIMAGES (BOX_ID, FILE_PATH) VALUES (?, ?)",
                    boxImage.getBoxId(), imageFile.getPath());
        } catch (DataAccessException | IOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(BoxImage boxImage) {
        try {
            checkDirectory();
            File imageFile = new File(filePathNaming(boxImage.getBoxId()));
            ImageIO.write(boxImage.getImage(), "png", imageFile);
            jdbcTemplate.update("UPDATE BOXIMAGES SET FILE_PATH=? WHERE BOX_ID=?", imageFile.getPath(), boxImage
                    .getBoxId());
        } catch (DataAccessException | IOException e) {
            throw new ServiceException(e);
        }
    }
}
