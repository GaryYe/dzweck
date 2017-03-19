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
import sepm.ss2017.e1625772.gui.properties.PropertyBoxBooking;
import sepm.ss2017.e1625772.service.BookingService;

import java.net.URL;
import java.time.LocalDate;
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
    private Button receiptButton;

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

    @FXML
    private Button addBoxButton;
    @FXML
    private Button deleteBoxButton;

    private ObservableList<PropertyBoxBooking> boxBookings;

    @FXML
    private ListView<PropertyBoxBooking> boxListView;

    @FXML
    private TextField boxIDTextField;
    @FXML
    private TextField agreedDailyRateTextField;
    @FXML
    private TextField horseNameTextField;

    private final String EDITING_STATE = "Editing";
    private final String CREATING_STATE = "Creating";

    private void clearAddBoxForm() {
        horseNameTextField.setText("");
        agreedDailyRateTextField.setText("");
        boxIDTextField.setText("");
    }

    @FXML
    public void addBox(ActionEvent actionEvent) {
        LOG.info("User has pressed the add box button");
        PropertyBoxBooking propertyBoxBooking = null;
        try {
            propertyBoxBooking = new PropertyBoxBooking(Long.valueOf(boxIDTextField.getText()),
                    horseNameTextField.getText(),
                    Double.valueOf(agreedDailyRateTextField.getText()));
        } catch (Exception e) {
            alertErrorMessage("Error while parsing your box " + e.getMessage());
            return;
        }
        boxBookings.addAll(propertyBoxBooking);
    }

    @FXML
    public void deleteBox(ActionEvent actionEvent) {
        LOG.info("User has requested deleting a box");
    }

    @FXML
    void search(ActionEvent event) {
        LOG.info("User has requested search by clicking the search button");

        LocalDate beginTime = searchBeginTimePicker.getValue();
        LocalDate endTime = searchEndTimePicker.getValue();

        // TODO: can this be improved?
        if (beginTime == null)
            beginTime = BookingService.MIN;
        if (endTime == null)
            endTime = BookingService.MAX;

        if (beginTime.isAfter(endTime)) {
            alertErrorMessage("End time can not be after begin time");
            return;
        }

        try {
            List<Booking> list = bookingService.findAllBetween(beginTime, endTime);
            LOG.info("Service found {} bookings", list.size());
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
        this.deleteButton.setDisable(true);
        this.receiptButton.setDisable(true);
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
            this.deleteButton.setDisable(false);
            this.receiptButton.setDisable(false);
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
            if (confirmationDialog("Do you really want to create the box?")) {
                try {
                    bookingService.create(booking);
                    search(event);
                    setStateCreateNew();
                } catch (BusinessLogicException e) {
                    alertErrorMessage("Something went wrong when the service created the box " + e.getMessage());
                }
            }
        } else {
            if (confirmationDialog("Do you really want to update the box?")) {
                try {
                    bookingService.delete(booking);
                    bookingService.create(booking); // TODO: can be improved
                    search(event);
                } catch (BusinessLogicException e) {
                    alertErrorMessage("Something went wrong when the service updated the box " + e.getMessage());
                }
            }
        }
    }

    @FXML
    void openReceipt(ActionEvent event) {
        LOG.info("User has pressed the receipt button");
        Booking booking = null;
        try {
            booking = parseBooking();
        } catch (Exception e) {
            e.printStackTrace();
        }

        alertErrorMessage("This is the receipt of booking: " + booking);
    }

    private Booking parseBooking() throws Exception {
        Booking booking = new Booking();
        try {
            booking.setId(Long.valueOf(idTextField.getText()));
            booking.setBeginTime(formBeginTimePicker.getValue());
            booking.setEndTime(formEndTimePicker.getValue());
            booking.setCustomerName(customerTextField.getText());
        } catch (NumberFormatException e) {
            throw new Exception(e);
        }
        return booking;
    }

    @FXML
    void resetSearch(ActionEvent event) {
        LOG.info("User has clicked the reset button");
        searchBeginTimePicker.setValue(null);
        searchEndTimePicker.setValue(null);
    }

    @FXML
    void delete(ActionEvent event) {
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
                search(event);
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

        boxBookings = FXCollections.observableArrayList();
        boxListView.setItems(boxBookings);

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

