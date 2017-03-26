package sepm.ss2017.e1625772.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxStatistics;
import sepm.ss2017.e1625772.exceptions.PresentationException;
import sepm.ss2017.e1625772.gui.forms.AbstractForm;
import sepm.ss2017.e1625772.service.BoxService;
import sepm.ss2017.e1625772.service.BoxStatisticsService;
import sepm.ss2017.e1625772.utils.DateUtils;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static org.slf4j.LoggerFactory.getLogger;
import static sepm.ss2017.e1625772.gui.utils.Dialogs.alertErrorMessage;
import static sepm.ss2017.e1625772.gui.utils.Dialogs.confirmationDialog;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class StatisticsController extends FXMLController {
    private static final Logger LOG = getLogger(StatisticsController.class);

    private final BoxStatisticsService boxStatisticsService;
    private final BoxService boxService;

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
    @FXML
    private Label previewLabel;
    @FXML
    private CheckBox mondayBox, tuesdayBox, thursdayBox, wednesdayBox, fridayBox, saturdayBox, sundayBox;

    private TimeRangeForm timeRangeForm;
    private UpdateForm updateForm;
    private WeekdayForm weekdayForm;

    @Autowired
    public StatisticsController(BoxStatisticsService boxStatisticsService, BoxService boxService) {
        this.boxStatisticsService = boxStatisticsService;
        this.boxService = boxService;
    }

    private boolean checkForm(AbstractForm form) {
        if (!form.parseAndValidate()) {
            alertErrorMessage(form.errorMessages());
            return false;
        }
        return true;
    }

    @FXML
    public void load() {
        LOG.info("User has clicked the preview button");
        if (!checkForm(timeRangeForm) || !checkForm(weekdayForm))
            return;

        stackedBarChart.getData().clear();
        List<XYChart.Series<String, Number>> allSeries = new ArrayList<>();
        BoxStatistics boxStatistics = boxStatisticsService.getStatistics(timeRangeForm.beginDate,
                timeRangeForm.endDate);
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            if (!weekdayForm.selectedFlags[dayOfWeek.getValue() - 1])
                continue;
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

    Box getBoxFromForm(boolean update) {
        if (!checkForm(timeRangeForm) || !checkForm(updateForm) || !checkForm(weekdayForm))
            throw new PresentationException("Form hasn't been checked for some reason");
        Box outlier = boxStatisticsService.findOutlierBox(timeRangeForm.beginDate, timeRangeForm.endDate,
                updateForm.updateBest, weekdayForm.selected);
        if (update)
            outlier.setDailyRate(newPrice(outlier.getDailyRate(), updateForm.updateAbsolute, updateForm.number));
        return outlier;
    }

    double newPrice(double currentPrice, boolean absolute, double amount) {
        double delta = absolute ? amount : currentPrice * amount / 100.0;
        return currentPrice + delta;
    }

    @FXML
    public void preview() {
        LOG.info("User has clicked the preview button");
        if (!checkForm(timeRangeForm) || !checkForm(updateForm))
            return;
        Box oldBox = getBoxFromForm(false);
        Box newBox = getBoxFromForm(true);
        if (oldBox == null) {
            previewLabel.setText("No box available in this time range");
        } else {
            previewLabel.setText(String.format("Box %s (id=%d): %.2f$/d -> %.2f$/d", oldBox.getName(), oldBox.getId(),
                    oldBox.getDailyRate(), newBox.getDailyRate()));
        }
    }

    @FXML
    public void update() {
        LOG.info("User has clicked the update button");
        if (!checkForm(timeRangeForm) || !checkForm(updateForm))
            return;
        Box box = getBoxFromForm(true);
        if (box == null) {
            alertErrorMessage("No box available in this time range");
            return;
        }
        if (confirmationDialog("Do you really want to update")) {
            boxService.updateBox(box);
        }
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
        weekdayForm = new WeekdayForm(Arrays.asList(mondayBox, tuesdayBox, wednesdayBox, thursdayBox, fridayBox,
                saturdayBox, sundayBox));
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
                beginDate = DateUtils.MIN;
            if (endDate == null)
                endDate = DateUtils.MAX;
        }

        @Override
        protected void check() {
            if (beginDate.isAfter(endDate)) {
                validationErrors.add("Begin date can not be after end date");
            }
        }
    }

    private class WeekdayForm extends AbstractForm {
        private final List<CheckBox> checkBoxes;
        List<DayOfWeek> selected;
        boolean selectedFlags[];

        private WeekdayForm(List<CheckBox> checkBoxes) {
            this.checkBoxes = checkBoxes;
        }


        @Override
        protected void doParse() {
            int i = 0;
            selected = new ArrayList<>();
            selectedFlags = new boolean[7];
            for (CheckBox checkBox : checkBoxes) {
                selectedFlags[i] = checkBox.isSelected();
                if (checkBox.isSelected()) {
                    selected.add(DayOfWeek.values()[i]);
                }
                i++;
            }
        }

        @Override
        protected void check() {

        }
    }
}
