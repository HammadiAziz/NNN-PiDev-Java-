package tn.esprit.controllers.ReclamationControllers.Admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import tn.esprit.services.ReclamationService;

import java.sql.SQLException;
import java.util.Map;
public class StatRec {

	@FXML
	PieChart Pichart;
	private final ReclamationService reclamationService=new ReclamationService();
	@FXML
	public  void initialize(){
		setPichart();
	}

	public void setPichart(){
		try {
			Map<String, Integer> statistics = reclamationService.getReclamationStatisticsByCategory();
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
			for (Map.Entry<String, Integer> entry : statistics.entrySet()) {
				pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
			}
			Pichart.setData(pieChartData);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
