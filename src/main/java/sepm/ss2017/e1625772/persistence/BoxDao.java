package sepm.ss2017.e1625772.persistence;

import sepm.ss2017.e1625772.domain.Box;

import java.util.Collection;

/**
 */
public interface BoxDao {
    Collection<Box> findAll();
    Collection<Box> findAll(Box box);
    void delete(Box box);
    void update(Box box);
}
