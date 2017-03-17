package sepm.ss2017.e1625772;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.exceptions.DataAccessException;
import sepm.ss2017.e1625772.persistence.BoxDAO;
import sepm.ss2017.e1625772.persistence.jdbc.JDBCBoxDAO;
import sepm.ss2017.e1625772.service.BoxService;

@Configuration
@ComponentScan(basePackages = "sepm.ss2017.e1625772")
public class Main {
    public static void main(String[] args) throws DataAccessException {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        if (false) {
            BoxDAO boxDAO = context.getBean(JDBCBoxDAO.class);
            boxDAO.create(new Box.BoxBuilder(30L).create());
        } else {
            BoxService boxService = context.getBean(BoxService.class);
            try {
                boxService.deleteBox(new Box.BoxBuilder(30L).create());
                boxService.createBox(new Box.BoxBuilder(30L).create());
            } catch (BusinessLogicException ignored) {
            }
        }
    }
}
