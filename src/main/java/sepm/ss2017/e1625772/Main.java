package sepm.ss2017.e1625772;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sepm.ss2017.e1625772.config.DataAccessConfig;
import sepm.ss2017.e1625772.domain.BoxImage;
import sepm.ss2017.e1625772.domain.builders.BoxBuilder;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.persistence.BoxImageDAO;
import sepm.ss2017.e1625772.persistence.jdbc.JDBCBoxDAO;
import sepm.ss2017.e1625772.service.BoxService;

@Configuration
@ComponentScan(basePackageClasses = {DataAccessConfig.class, BoxImageDAO.class, BoxService.class})
public class Main {
    public static void main(String[] args) throws DataAccessException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        if (false) {
            BoxDAO boxDAO = context.getBean(JDBCBoxDAO.class);
            boxDAO.create(new BoxBuilder(30L).create());
        } else {
            BoxService boxService = context.getBean(BoxService.class);
            try {
                boxService.deleteBox(new BoxBuilder(30L).create());
                boxService.createBox(new BoxBuilder(30L).create());
                BoxImage boxImage = boxService.findImage(1L);
                boxService.saveImage(boxImage);
                int i = 3;
            } catch (ServiceException ignored) {
            }
        }
    }
}
