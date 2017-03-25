package sepm.ss2017.e1625772.gui;

import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import sepm.ss2017.e1625772.domain.Box;
import sepm.ss2017.e1625772.domain.BoxStatistics;
import sepm.ss2017.e1625772.service.BookingService;
import sepm.ss2017.e1625772.service.BoxStatisticsService;

import java.net.URL;
import java.time.DayOfWeek;
import java.util.*;

/**
 * @author Gary Ye
 * @version %I% %G%
 */
@Controller
public class StatisticsController extends FXMLController {
    // http://docs.oracle.com/javafx/2/charts/bar-chart.htm
    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    private final BoxStatisticsService boxStatisticsService;
    @FXML
    private StackedBarChart<String, Number> stackedBarChart;

    @Autowired
    public StatisticsController(BoxStatisticsService boxStatisticsService) {
        this.boxStatisticsService = boxStatisticsService;
    }

    @FXML
    public void load() {
        stackedBarChart.getData().clear();
        List<XYChart.Series<String, Number>> allSeries = new ArrayList<>();
        // todo
        BoxStatistics boxStatistics = boxStatisticsService.getStatistics(BookingService.MIN, BookingService.MAX);
        Map<Box, Integer[]> map = new HashMap<>();
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

    @Value("ui/stats.fxml")
    @Override
    public void setFxmlFilePath(String filePath) {
        this.fxmlFilePath = filePath;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stackedBarChart.setAnimated(false);
        stackedBarChart.getYAxis().setLabel("Number of reservations");
    }
}
