package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import tn.esprit.entitiesModel.SuiviLiv;
import tn.esprit.service.LivraisonService;


import java.net.URL;
import java.util.ResourceBundle;

public class MapController implements Initializable {

    public Label dateCommande;
    public Label location;
    public Label livree;
    public Label id;

    @FXML
    private WebView mapView;

    private WebEngine webEngine;
    private final LivraisonService ls = new LivraisonService();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        webEngine = mapView.getEngine();
       // int livraisonId = (int) SessionManager.getSession("userId");
        // Use Livraison ID to fetch SuiviLiv data
        SuiviLiv suiviLiv = ls.getSuiviLivByLivraisonId(16);
        dateCommande.setText(suiviLiv.getDateCommande());
        location.setText(suiviLiv.getLocation());
        livree.setText(suiviLiv.isLivree() ? "Oui" : "Non");
        id.setText(String.valueOf(suiviLiv.getId()));

        // Update Google Map URL with latitude and longitude
        updateGoogleMap(suiviLiv.getLocation());
    }

    private void updateGoogleMap(String location) {
        // Parse location string to extract latitude and longitude
        String[] parts = location.split("\\(Latitude: |, Longitude: |\\)");
        if (parts.length >= 3) {
            String latitude = parts[1];
            String longitude = parts[2];

            // Update Google Map URL with latitude and longitude
            String mapUrl = "https://www.google.com/maps/embed/v1/place?key=AIzaSyCBAskCbIMqqRUoHd41aETAnYyUu8KeXUU&q=" + latitude + "," + longitude;
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
                    "        src=\"" + mapUrl + "\">\n" +
                    "    </iframe>\n" +
                    "</body>\n" +
                    "</html>";

            // Load updated Google Map HTML content
            webEngine.loadContent(htmlContent);
        } else {
            System.out.println("Invalid location format: " + location);
        }
    }
}