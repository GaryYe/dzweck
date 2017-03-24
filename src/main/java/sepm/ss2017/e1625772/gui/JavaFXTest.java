package sepm.ss2017.e1625772.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import sepm.ss2017.e1625772.exceptions.ServiceException;

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

    private static void showError(Thread t, Throwable e) {
        LOG.error("Error occurred", e);
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in " + t);
        }
    }

    private static void showErrorDialog(Throwable e) {
        String errorMessage = "An unexpected occurred while processing your request";
        if (e instanceof ServiceException) {
            FXUtils.alertErrorMessage(errorMessage);
        } else {
            FXUtils.alertExceptionMessage(new Exception(e), errorMessage);
        }
    }


    @Override
    public void start(Stage stage) throws IOException {
        Thread.setDefaultUncaughtExceptionHandler(JavaFXTest::showError);
        ApplicationContext appContext = new AnnotationConfigApplicationContext(JavaFXTest.class);
        FXMLController demoController = appContext.getBean(BookingController.class);

        stage.setScene(new Scene((Parent) demoController.getView()));
        stage.setTitle("Wendy's horse pension");
        stage.show();

        LOG.info("The UI has started");
    }
}
