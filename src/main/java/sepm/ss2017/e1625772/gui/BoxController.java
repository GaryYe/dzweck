package sepm.ss2017.e1625772.gui;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxImage;
import sepm.ss2017.e1625772.domain.builders.BoxBuilder;
import sepm.ss2017.e1625772.exceptions.ServiceException;
import sepm.ss2017.e1625772.service.BoxService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static sepm.ss2017.e1625772.gui.FXUtils.alertErrorMessage;
import static sepm.ss2017.e1625772.gui.FXUtils.confirmationDialog;

/**
 * sepm.ss2017.e1625772.gui.BoxSearchingController
 */
@Component
public class BoxController extends FXMLController {
    private static final Logger LOG = LoggerFactory.getLogger(BoxController.class);

    private final BoxService boxService;
    private final String EDITING_STATE = "Viewing";
    private final String CREATING_STATE = "Creating";
    private ObservableList<PropertyBox> boxObservableList;
    @FXML
    private Label nameLabel;
    @FXML
    private Button searchButton;
    @FXML
    private TableColumn<PropertyBox, String> nameColumn;
    @FXML
    private TableView<PropertyBox> boxSearchTable;
    @FXML
    private TextField searchName;
    @FXML
    private Button resetButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button createNewButton;
    @FXML
    private Button deleteButton;
    @FXML
    private TableColumn<PropertyBox, Long> idColumn;
    // DETAIL VIEW BEGIN
    @FXML
    private TextField boxNameTextBox;
    @FXML
    private TextField boxIdTextBox;
    @FXML
    private Button selectImageButton;
    @FXML
    private ImageView imageView;
    @FXML
    private CheckBox hasWindowsCheckbox;
    @FXML
    private CheckBox indoorCheckbox;
    @FXML
    private TextField areaTextBox;
    @FXML
    private TextField dailyRateTextBox;
    @FXML
    private Label currentStateLabel;
    private BufferedImage selectedImage;

    // DETAIL VIEW END

    // http://stackoverflow.com/questions/26424769/javafx8-how-to-create-listener-for-selection-of-row-in-tableview

    @Autowired
    public BoxController(BoxService boxService) {
        super();
        this.boxService = boxService;
    }

    private void setCreateNewState() {
        boxIdTextBox.setText("");
        boxIdTextBox.setEditable(true);

        boxNameTextBox.setText("");
        areaTextBox.setText("");
        dailyRateTextBox.setText("");
        hasWindowsCheckbox.setSelected(false);
        indoorCheckbox.setSelected(false);
        currentStateLabel.setText(CREATING_STATE);
        deleteButton.setDisable(true);
        imageView.setImage(null);
        selectedImage = null;
    }

    private void loadSelectedImage() {
        if (selectedImage == null) {
            imageView.setImage(null);
        } else {
            Image fxImage = SwingFXUtils.toFXImage(selectedImage, null);
            imageView.setImage(fxImage);
        }
    }

    @FXML
    public void selectImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        // http://stackoverflow.com/questions/13585590/how-to-get-parent-window-in-fxml-controller
        File file = fileChooser.showOpenDialog(((Node) event.getTarget()).getScene().getWindow());
        if (file != null) {
            try {
                selectedImage = ImageIO.read(file);
                loadSelectedImage();
            } catch (IOException e) {
                alertErrorMessage("Could not read the given file");
            }
        }
    }

    @FXML
    public void search(ActionEvent actionEvent) {
        LOG.error("User has requested a search via the search button");
        try {
            // TODO: Replace null LUL
            List<Box> boxes = boxService.findBoxes(null);
            boxObservableList.clear();
            for (Box box : boxes)
                boxObservableList.addAll(new PropertyBox(box));
            boxSearchTable.refresh();
        } catch (ServiceException e) {
            LOG.error("Error happened while searching for the boxes");
            alertErrorMessage("Error while calling the service for searching the boxes");
        }
    }

    @FXML
    public void createNew(ActionEvent actionEvent) {
        LOG.error("User has requested creating a new Box");
        if (confirmationDialog("If you want to create a new one, the entered data, which might be empty, will be lost" +
                "."))
            setCreateNewState();
    }

    @FXML
    public void delete(ActionEvent actionEvent) {
        LOG.error("User has requested deleting the current box");

        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You can not delete the box you are currently creating");
            alert.showAndWait();
        } else {
            Box box = new BoxBuilder(Long.valueOf(boxIdTextBox.getText())).create();
            if (confirmationDialog("The current box you selected has the id = " + box.getId())) {
                try {
                    boxService.deleteBox(box);
                } catch (ServiceException e) {
                    LOG.error("Error while deleting the box");
                    alertErrorMessage("Error happened while telling the service to delete the box");
                }
                search(actionEvent);
                setCreateNewState();
            }
        }
    }

    private Box parseCurrentBox() throws Exception {
        Box box = new Box();
        try {
            box.setId(Long.valueOf(boxIdTextBox.getText()));
            box.setName(boxNameTextBox.getText());
            box.setArea(Double.valueOf(areaTextBox.getText()));
            box.setDailyRate(Double.valueOf(dailyRateTextBox.getText()));
            box.setHasWindows(hasWindowsCheckbox.isSelected());
            box.setIndoor(indoorCheckbox.isSelected());
        } catch (Exception e) {
            // TODO: Something something / Better usability
            LOG.error("Something went wrong parsing the box", e);
            throw new Exception(e);
        }
        return box;
    }

    private void saveSelectedImage(Long boxId) {
        if (selectedImage != null) {
            boxService.saveImage(new BoxImage(boxId, selectedImage));
        }
    }

    @FXML
    public void save(ActionEvent actionEvent) {
        LOG.error("User clicked the save button");

        Box box = null;
        try {
            box = parseCurrentBox();
        } catch (Exception e) {
            LOG.error("The given box is invalid", e);
            alertErrorMessage("Sorry, but we could not parse the given box.");
            return;
        }

        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            if (confirmationDialog("Do you really want to create the box?")) {
                boxService.createBox(box);
                saveSelectedImage(box.getId());
            }
        } else {
            if (confirmationDialog("Do you really want to update the box?")) {
                boxService.updateBox(box);
                saveSelectedImage(box.getId());
            }
        }

        setStateViewingBox(box.getId());
    }

    /**
     * Sets the form state to a viewing state of the given box.
     *
     * @param id the id of the box (maybe take Box instead?)
     */
    private void setStateViewingBox(Long id) {
        if (id == null)
            throw new IllegalArgumentException();
        LOG.info("Loading view of box = {}", id);

        Box box = null;
        try {
            box = boxService.findBox(id);
            if (box == null)
                throw new ServiceException("Box not found");
        } catch (ServiceException e) {
            LOG.error("Something went wrong with retrieving the box id={}", id, e);
            alertErrorMessage(String.format("Error while retrieving box details of id=%d from service", id));
            return;
        }

        boxIdTextBox.setText(String.valueOf(id));
        boxIdTextBox.setEditable(false);

        boxNameTextBox.setText(box.getName());
        // Do
        areaTextBox.setText(String.valueOf(box.getArea()));
        dailyRateTextBox.setText(String.valueOf(box.getDailyRate()));
        hasWindowsCheckbox.setSelected(box.hasWindows());
        indoorCheckbox.setSelected(box.isIndoor());
        currentStateLabel.setText(EDITING_STATE);
        deleteButton.setDisable(false);

        BoxImage boxImage = boxService.findImage(id);
        if (boxImage == null)
            selectedImage = null;
        else
            selectedImage = boxImage.getImage();
        loadSelectedImage();
    }

    @Value("ui/box.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.boxObservableList = FXCollections.observableArrayList();
        this.boxSearchTable.setItems(boxObservableList);
        idColumn.setCellValueFactory(new PropertyValueFactory<PropertyBox, Long>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<PropertyBox, String>("name"));
        boxSearchTable.getSelectionModel()
                .selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOG.info("Selected id = {}", newSelection.getId());
                setStateViewingBox(newSelection.getId());
            }
        });
        setCreateNewState();
    }

    public static class PropertyBox {
        private final SimpleLongProperty id;
        private final SimpleStringProperty name;

        public PropertyBox(Box box) {
            this(box.getId(), box.getName());
        }

        public PropertyBox(Long id, String name) {
            this.id = new SimpleLongProperty(id);
            this.name = new SimpleStringProperty(name);
        }

        public long getId() {
            return id.get();
        }

        public void setId(long id) {
            this.id.set(id);
        }

        public SimpleLongProperty idProperty() {
            return id;
        }

        public String getName() {
            return name.get();
        }

        public void setName(String name) {
            this.name.set(name);
        }

        public SimpleStringProperty nameProperty() {
            return name;
        }
    }
}
