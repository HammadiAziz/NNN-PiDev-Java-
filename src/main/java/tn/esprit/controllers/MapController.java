package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    @FXML
    private WebView mapView;

    private WebEngine webEngine;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = mapView.getEngine();
        loadGoogleMap();
    }

    private void loadGoogleMap() {
        String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Embedded Google Map</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <iframe\n" +
                "        width=\"800\"\n" +
                "        height=\"600\"\n" +
                "        style=\"border:0\"\n" +
                "        loading=\"lazy\"\n" +
                "        allowfullscreen\n" +
                "        src=\"https://www.google.com/maps/embed/v1/place?key=AIzaSyCBAskCbIMqqRUoHd41aETAnYyUu8KeXUU=36.89175280997547,10.178348993426216\">\n" +
                "    </iframe>\n" +
                "</body>\n" +
                "</html>";

        webEngine.loadContent(htmlContent);
    }
}