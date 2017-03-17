package sepm.ss2017.e1625772.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.service.BoxService;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Service
public class BoxServiceImpl implements BoxService {
    private static final Logger LOG = LoggerFactory.getLogger(BoxServiceImpl.class);
    private final BoxDAO boxDAO;

    @Autowired
    public BoxServiceImpl(BoxDAO boxDAO) {
        this.boxDAO = boxDAO;
    }

    @Override
    public void createBox(Box box) throws BusinessLogicException {
        try {
            boxDAO.create(box);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public Box findBox(Long id) throws BusinessLogicException {
        try {
            return boxDAO.findOne(id);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public void updateBox(Box box) throws BusinessLogicException {
        try {
            boxDAO.update(box);
        } catch (DataAccessException e) {
            throw new BusinessLogicException(e);
        }
    }

    @Override
    public void deleteBox(Box box) throws BusinessLogicException {
        try {
            boxDAO.delete(box);
        } catch (DataAccessException e) {
            LOG.error("Error while deleting box = {}", box.toString(), e);
            throw new BusinessLogicException(e);
        }
    }
}
