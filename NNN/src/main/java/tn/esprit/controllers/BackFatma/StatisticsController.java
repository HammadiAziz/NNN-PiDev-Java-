package tn.esprit.controllers.BackFatma;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import tn.esprit.services.EventService;

import java.net.URL;
import java.time.Month;
import java.util.Map;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label totalDurationLabel;

    @FXML
    private Label maxDurationLabel;

    @FXML
    private Label minDurationLabel;

    private final EventService eventService = new EventService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set label texts


        // Get events per month data
        Map<Month, Integer> eventsPerMonth = eventService.getEventsPerMonth();

        // Populate pie chart
        for (Map.Entry<Month, Integer> entry : eventsPerMonth.entrySet()) {
            pieChart.getData().add(new PieChart.Data(entry.getKey().toString(), entry.getValue()));
        }

        // Find the most eventful month
        Month mostEventfulMonth = null;
        int maxEvents = 0;
        for (Map.Entry<Month, Integer> entry : eventsPerMonth.entrySet()) {
            if (entry.getValue() > maxEvents) {
                maxEvents = entry.getValue();
                mostEventfulMonth = entry.getKey();
            }
        }

        // Set title
        if (mostEventfulMonth != null) {
            pieChart.setTitle("Most Eventful Month: " + mostEventfulMonth.toString());
        }
    }
}
