package sepm.ss2017.e1625772.gui;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Configuration
@ComponentScan(basePackages = "sepm.ss2017.e1625772")
public class JavaFXTest extends Application {
    private static final Logger LOG = LoggerFactory.getLogger(JavaFXTest.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(JavaFXTest.class);
        BoxSearchingController demoController = appContext.getBean(BoxSearchingController.class);

        stage.setScene(new Scene((Parent) demoController.getView()));
        stage.setTitle("Wendy's horse mansion");
        stage.show();

        LOG.info("The UI has started");
    }
}
