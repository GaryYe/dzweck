package sepm.ss2017.e1625772.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sepm.ss2017.e1625772.domain.Booking;
import sepm.ss2017.e1625772.exceptions.BusinessLogicException;
import sepm.ss2017.e1625772.gui.properties.PropertyBooking;
import sepm.ss2017.e1625772.service.BookingService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class BookingController extends FXMLController {
    private static final Logger LOG = LoggerFactory.getLogger(BookingController.class);

    @FXML
    private Button createNewButton;

    @FXML
    private TextField idTextField;

    @FXML
    private TableView<PropertyBooking> searchTable;
    @FXML
    private TableColumn<PropertyBooking, Long> idColumn;
    @FXML
    private TableColumn<PropertyBooking, String> customerColumn;
    @FXML
    private TableColumn<PropertyBooking, String> beginColumn;
    @FXML
    private TableColumn<PropertyBooking, String> endColumn;

    @FXML
    private Button searchButton;

    @FXML
    private DatePicker searchEndTimePicker;

    @FXML
    private Button deleteButton;

    @FXML
    private DatePicker formBeginTimePicker;

    @FXML
    private TextField customerTextField;

    @FXML
    private DatePicker searchBeginTimePicker;

    @FXML
    private Label currentStateLabel;

    @FXML
    private DatePicker formEndTimePicker;

    @FXML
    private Button saveButton;

    @FXML
    private Button resetButton;

    private final String EDITING_STATE = "Editing";
    private final String CREATING_STATE = "Creating";

    @FXML
    void search(ActionEvent event) {
        LOG.info("User has requested search by clicking the search button");

        // Search all or with ranges?
        try {
            List<Booking> list = bookingService.findAll();
            searchTableList.clear();
            for (Booking booking : list) {
                searchTableList.addAll(new PropertyBooking(booking));
            }
        } catch (BusinessLogicException e) {
            alertErrorMessage(e.getMessage());
        }
    }

    private void setStateCreateNew() {
        this.currentStateLabel.setText(CREATING_STATE);
        this.idTextField.setText("");
        this.idTextField.setEditable(true);
        this.formBeginTimePicker.setValue(null);
        this.formEndTimePicker.setValue(null);
        this.customerTextField.setText("");
    }

    private void setStateEditing(Booking booking) {
        if (booking == null) {
            setStateCreateNew();
        } else {
            this.currentStateLabel.setText(EDITING_STATE);
            this.idTextField.setText(String.valueOf(booking.getId()));
            this.idTextField.setEditable(false);
            this.formBeginTimePicker.setValue(booking.getBeginTime());
            this.formEndTimePicker.setValue(booking.getEndTime());
            this.customerTextField.setText(booking.getCustomerName());
        }
    }

    @FXML
    void createNew(ActionEvent event) {
        if (confirmationDialog("Do you want to create a new box (the entered data, if any, will be lost)?"))
            setStateCreateNew();
    }

    @FXML
    void save(ActionEvent event) {
        Booking booking = null;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            alertErrorMessage("Can not parse the book: " + e.getMessage());
            return;
        }

        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            if (confirmationDialog("Do you really want to create the box")) {
                try {
                    bookingService.create(booking);
                } catch (BusinessLogicException e) {
                    alertErrorMessage("Something went wrong when the serviced created the box " + e.getMessage());
                }
            }
        } else {

        }
    }

    private Booking parseBooking() throws Exception {
        Booking booking = new Booking();
        try {
            booking.setId(Long.valueOf(idTextField.getText()));
            booking.setBeginTime(formBeginTimePicker.getValue());
            booking.setEndTime(formEndTimePicker.getValue());
            booking.setCustomerName(booking.getCustomerName());
        } catch (NumberFormatException e) {
            throw new Exception(e);
        }
        return booking;
    }

    @FXML
    void delete(ActionEvent event) {
        if (currentStateLabel.getText().equals(CREATING_STATE)) {
            alertErrorMessage("You can not delete a box you are creating");
            return;
        }

        Booking booking = null;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            alertErrorMessage("Something went wrong when parsing the box" + e.getMessage());
            return;
        }

        if (confirmationDialog("Do you really want to delete?")) {
            try {
                bookingService.delete(booking);
                setStateCreateNew();
            } catch (BusinessLogicException e) {
                alertErrorMessage("Something went wrong when calling the service to delete the given box" + e.getMessage());
            }
        }
    }

    private ObservableList<PropertyBooking> searchTableList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchTableList = FXCollections.observableArrayList();
        searchTable.setItems(searchTableList);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        beginColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        searchTable.getSelectionModel().selectedItemProperty().addListener((observable, oldSelection, newSelection) -> {
            if (newSelection != null) {
                LOG.info("Selected id = {}", newSelection.getId());
                try {
                    Booking booking = bookingService.findOne(newSelection.getId());
                    setStateEditing(booking);
                } catch (BusinessLogicException e) {
                    alertErrorMessage(String.format("The service could not find the box with id = %d", newSelection
                            .getId()));
                }
            }
        });

        setStateCreateNew();
    }

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Value("ui/booking.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }
}

