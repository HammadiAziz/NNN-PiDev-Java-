package tn.esprit.util;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

public class Map {
    public static void main(String[] args) {
        // Remplacez "YOUR_API_KEY" par votre clé d'API Google Maps
        GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyCBAskCbIMqqRUoHd41aETAnYyUu8KeXUU\n").build();

        try {
            GeocodingResult[] results = GeocodingApi.geocode(context, "1600 Amphitheatre Parkway, Mountain View, CA").await();

            if (results.length > 0) {
                System.out.println("Latitude: " + results[0].geometry.location.lat);
                System.out.println("Longitude: " + results[0].geometry.location.lng);
            } else {
                System.out.println("Aucun résultat trouvé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
