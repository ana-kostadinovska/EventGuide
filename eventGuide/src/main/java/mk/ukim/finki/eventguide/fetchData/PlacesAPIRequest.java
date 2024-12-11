package mk.ukim.finki.eventguide.fetchData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class PlacesAPIRequest {
    public static void main(String[] args) {
        String apiUrl = "https://places.googleapis.com/v1/places:searchNearby";
        String apiKey = ""; //todo
        StringBuilder response = new StringBuilder();
//        karaoke?, night_club, bar, cafe, pub, wine_bar?
//        languageCode ako treba da se dodaj i googleMapsUri
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
                    response = new StringBuilder();
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

        String csvFile = "places.csv";
        StringBuilder sb = new StringBuilder();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());

            try (FileWriter writer = new FileWriter(csvFile)) {

                writer.append("Name, Address, Type, PhoneNumber, OpeningHours\n");

                JsonNode places = rootNode.get("places");
                for (JsonNode place : places) {
                    String name = place.get("displayName").get("text").asText();
                    String address = place.get("formattedAddress").asText();//todo da se zema samo prviot del pred zapirkata
                    String phoneNumber = place.has("nationalPhoneNumber") && !place.get("nationalPhoneNumber").isNull()
                            ? place.get("nationalPhoneNumber").asText()
                            : "N/A";
                    String type = place.get("primaryType").asText();
                    JsonNode workingHours = place.get("regularOpeningHours").withArray("weekdayDescriptions");

                    writer.append(name).append(", ").append(address).append(", ").append(type).append(", ")
                            .append(phoneNumber).append(", ").append(workingHours.toString()).append("\n");
                    sb.append(name).append(", ").append(address).append(", ").append(type).append(", ")
                            .append(phoneNumber).append(", ").append(workingHours.toString()).append("\n");

                    writer.flush();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            System.out.println("CSV file saved: " + csvFile);
            System.out.println(sb.toString());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}