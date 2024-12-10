package mk.ukim.finki.eventguide.fetchData;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PlacesAPIRequest {
    public static void main(String[] args) {
        String apiUrl = "https://places.googleapis.com/v1/places:searchNearby";
        String apiKey = ""; //todo
//        karaoke?, night_club, bar, cafe, pub, wine_bar?
//        languageCode ako treba da se dodaj
        String requestBody = """
                    {
                      "includedTypes": ["night_club", "bar", "pub"],
                      "maxResultCount": 20,
                      "locationRestriction": {
                        "circle": {
                          "center": {
                            "latitude": 41.9875179,
                            "longitude": 21.4319176
                          },
                          "radius": 5000.0
                        }
                      }
                    }
                """;

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("X-Goog-Api-Key", apiKey);
            connection.setRequestProperty("X-Goog-FieldMask", "places.displayName,places.formattedAddress,places.primaryType,places.nationalPhoneNumber,places.regularOpeningHours");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = requestBody.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (var reader = new java.io.BufferedReader(
                        new java.io.InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String responseLine;
                    StringBuilder response = new StringBuilder();
                    while ((responseLine = reader.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    System.out.println("Response: " + response);
                }
            } else {
                System.err.println("Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}