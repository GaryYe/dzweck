package sepm.ss2017.e1625772.gui;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import org.springframework.beans.factory.InitializingBean;

import java.io.IOException;
import java.net.URL;

/**
 * https://codelife.de/2015/02/27/javafx-8-with-spring-integration/
 *
 * @version %I% %G%
 */
public abstract class FXMLController implements InitializingBean, Initializable {
    protected Node view;
    protected String fxmlFilePath;

    public FXMLController() {
        super();
    }

    public abstract void setFxmlFilePath(String filePath);

    @Override
    public void afterPropertiesSet() throws Exception {
        loadFXML();
    }

    protected final void loadFXML() throws IOException {
        URL url = getClass().getClassLoader().getResource(fxmlFilePath);
        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(this);
        this.view = loader.load();
    }

    public Node getView() {
        return view;
    }
}
