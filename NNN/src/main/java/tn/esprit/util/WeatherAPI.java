package tn.esprit.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class WeatherAPI {

    private static final String API_KEY = "031ac3e75d61fc4c1fa0d28cab60561d";
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&appid=%s";

    public static String getWeather(double latitude, double longitude) throws IOException {
        String urlString = String.format(API_URL, latitude, longitude, API_KEY);

        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        JSONObject json = new JSONObject(response.toString());
        String locationName = json.getString("name");
        JSONObject main = json.getJSONObject("main");
        double tempKelvin = main.getDouble("temp");
        double tempCelsius = tempKelvin - 273.15; // Convert from Kelvin to Celsius

        return String.format("Location: %s%nTemperature: %.1f°C", locationName, tempCelsius);
    }
}
