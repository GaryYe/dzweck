package sepm.ss2017.e1625772.gui;

import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxStatistics;
import sepm.ss2017.e1625772.gui.forms.AbstractForm;
import sepm.ss2017.e1625772.service.BookingService;
import sepm.ss2017.e1625772.service.BoxStatisticsService;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static sepm.ss2017.e1625772.gui.FXUtils.alertErrorMessage;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class StatisticsController extends FXMLController {
    private final BoxStatisticsService boxStatisticsService;

    @FXML
    private StackedBarChart<String, Number> stackedBarChart;
    @FXML
    private RadioButton absoluteRadio;
    @FXML
    private RadioButton percentageRadio;
    @FXML
    private RadioButton bestRadio;
    @FXML
    private RadioButton worstRadio;
    @FXML
    private TextField numberTextField;
    @FXML
    private DatePicker beginTimePicker;
    @FXML
    private DatePicker endTimePicker;

    private TimeRangeForm timeRangeForm;
    private UpdateForm updateForm;

    @Autowired
    public StatisticsController(BoxStatisticsService boxStatisticsService) {
        this.boxStatisticsService = boxStatisticsService;
    }

    @FXML
    public void load() {
        if (!timeRangeForm.parseAndValidate()) {
            alertErrorMessage(timeRangeForm.errorMessages());
            return;
        }

        stackedBarChart.getData().clear();
        List<XYChart.Series<String, Number>> allSeries = new ArrayList<>();
        BoxStatistics boxStatistics = boxStatisticsService.getStatistics(timeRangeForm.beginDate,
                timeRangeForm.endDate);
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            XYChart.Series<String, Number> daySeries = new XYChart.Series<>();
            daySeries.setName(dayOfWeek.toString());
            for (Box box : boxStatistics.boxes()) {
                int value = boxStatistics.numberOfReservations(box, dayOfWeek);
                daySeries.getData().addAll(new XYChart.Data(box.getName(), value));
            }
            allSeries.add(daySeries);
        }
        for (XYChart.Series<String, Number> daySeries : allSeries)
            stackedBarChart.getData().addAll(daySeries);
    }

    @FXML
    public void preview() {
    }

    @FXML
    public void update() {

    }

    @Value("ui/stats.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackedBarChart.setAnimated(false);
        timeRangeForm = new TimeRangeForm(beginTimePicker, endTimePicker);
        updateForm = new UpdateForm(absoluteRadio, bestRadio, numberTextField);
    }

    private class UpdateForm extends AbstractForm {
        private final RadioButton absoluteRadio;
        private final RadioButton bestRadio;
        private final TextField numberTextField;

        boolean updateBest;
        boolean updateAbsolute;
        Double number;

        private UpdateForm(RadioButton absoluteRadio, RadioButton bestRadio, TextField numberTextField) {
            this.absoluteRadio = absoluteRadio;
            this.bestRadio = bestRadio;
            this.numberTextField = numberTextField;
        }

        @Override
        protected void doParse() {
            updateAbsolute = absoluteRadio.isSelected();
            updateBest = bestRadio.isSelected();
            try {
                number = Double.valueOf(numberTextField.getText());
            } catch (NumberFormatException e) {
                parsingErrors.add("The entered delta is not a number");
            }
        }

        public void check() {
            if (number == null) {
                validationErrors.add("You have to enter a number");
            } else {
                if (!updateAbsolute && Math.abs(number) > 100.0) {
                    validationErrors.add("You have chosen percentage, so choose a number between -100 and 100");
                }
            }
        }
    }

    private class TimeRangeForm extends AbstractForm {
        private final DatePicker beginTimePicker;
        private final DatePicker endTimePicker;

        LocalDate beginDate;
        LocalDate endDate;

        public TimeRangeForm(DatePicker beginTimePicker, DatePicker endTimePicker) {
            this.beginTimePicker = beginTimePicker;
            this.endTimePicker = endTimePicker;
        }

        @Override
        protected void doParse() {
            beginDate = beginTimePicker.getValue();
            endDate = endTimePicker.getValue();

            if (beginDate == null)
                beginDate = BookingService.MIN;
            if (endDate == null)
                endDate = BookingService.MAX;
        }

        @Override
        protected void check() {
            if (beginDate.isAfter(endDate)) {
                validationErrors.add("Begin date can not be after end date");
            }
        }
    }
}
