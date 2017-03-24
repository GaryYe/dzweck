package sepm.ss2017.e1625772.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

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
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in " + t);
        }
    }

    private static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        URL url = JavaFXTest.class.getClassLoader().getResource("ui/error.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        try {
            Parent root = loader.load();
            ((ErrorController) loader.getController()).setErrorText(errorMsg.toString());
            dialog.setScene(new Scene(root, 250, 400));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
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
