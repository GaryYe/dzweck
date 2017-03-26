package sepm.ss2017.e1625772.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxImage;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.persistence.BoxImageDAO;
import sepm.ss2017.e1625772.service.BoxService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BoxServiceImpl implements BoxService {
    private static final Logger LOG = LoggerFactory.getLogger(BoxServiceImpl.class);
    private final BoxDAO boxDAO;
    private final BoxImageDAO boxImageDAO;

    @Autowired
    public BoxServiceImpl(BoxDAO boxDAO, BoxImageDAO boxImageDAO) {
        this.boxDAO = boxDAO;
        this.boxImageDAO = boxImageDAO;
    }

    @Override
    public void createBox(Box box) {
        try {
            boxDAO.create(box);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Box findBox(Long id) {
        try {
            return boxDAO.findOne(id);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Box> findBoxes(Box box) {
        try {
            return new ArrayList<>(boxDAO.findAll(box));
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void updateBox(Box box) {
        try {
            boxDAO.update(box);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteBox(Box box) {
        try {
            boxDAO.delete(box);
        } catch (DataAccessException e) {
            LOG.error("Error while deleting box = {}", box.toString(), e);
            throw new ServiceException(e);
        }
    }

    @Override
    public BoxImage findImage(Long boxId) {
        try {
            return boxImageDAO.findByBox(boxId);
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void saveImage(BoxImage boxImage) {
        try {
            if (boxImageDAO.findByBox(boxImage.getBoxId()) == null) {
                boxImageDAO.create(boxImage);
                LOG.debug("Image of box {} has been created", boxImage.getBoxId());
            } else {
                boxImageDAO.update(boxImage);
                LOG.debug("Image of box {} has been updated", boxImage.getBoxId());
            }
        } catch (DataAccessException e) {
            throw new ServiceException(e);
        }
    }
}
