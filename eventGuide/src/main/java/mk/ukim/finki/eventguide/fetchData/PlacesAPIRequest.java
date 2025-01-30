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
import java.util.ArrayList;
import java.util.List;

public class PlacesAPIRequest {
    public static void main(String[] args) {
        String apiUrl = "https://places.googleapis.com/v1/places:searchNearby";
        String apiKey = "";
        StringBuilder response = new StringBuilder();
        List<String> requests = new ArrayList<>();
        String requestBody1 = """
                    {
                      "includedTypes": ["night_club", "bar", "pub"],
                      "maxResultCount": 20,
                      "locationRestriction": {
                        "circle": {
                          "center": {
                            "latitude": 42.000636,
                            "longitude": 21.413222
                          },
                          "radius": 3000.0
                        }
                      }
                    }
                """;
        String requestBody2 = """
                    {
                      "includedTypes": ["night_club", "bar", "pub"],
                      "maxResultCount": 20,
                      "locationRestriction": {
                        "circle": {
                          "center": {
                            "latitude": 41.992699,
                            "longitude": 21.433445
                          },
                          "radius": 5000.0
                        }
                      }
                    }
                """;
        String requestBody3 = """
                    {
                      "includedTypes": ["bar", "pub"],
                      "maxResultCount": 20,
                      "locationRestriction": {
                        "circle": {
                          "center": {
                            "latitude": 41.982741,
                            "longitude": 21.471009
                          },
                          "radius": 5000.0
                        }
                      },
                      "languageCode": "mk"
                    }
                """;

        requests.add(requestBody1);
        requests.add(requestBody2);
        requests.add(requestBody3);

        String csvFile = "places.csv";
        try (FileWriter writer = new FileWriter(csvFile)) {

            writer.append("Name;Address;Primary Type;Types;PhoneNumber;OpeningHours;Maps link;Local website\n");

            for (String request : requests) {
                try {
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Content-Type", "application/json");
                    connection.setRequestProperty("X-Goog-Api-Key", apiKey);
                    connection.setRequestProperty("X-Goog-FieldMask", "places.displayName,places.shortFormattedAddress,places.primaryType,places.types,places.nationalPhoneNumber,places.regularOpeningHours,places.googleMapsUri,places.websiteUri");
                    connection.setDoOutput(true);

                    try (OutputStream os = connection.getOutputStream()) {
                        byte[] input = request.getBytes(StandardCharsets.UTF_8);
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
                        }
                    } else {
                        System.err.println("Error: " + responseCode);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.toString());
                  
                    JsonNode places = rootNode.get("places");
                    for (JsonNode place : places) {
                        String name = place.get("displayName").get("text").asText();
                        String address = place.get("shortFormattedAddress").asText();
                        JsonNode types = place.get("types");
                        String phoneNumber = place.has("nationalPhoneNumber") && !place.get("nationalPhoneNumber").isNull()
                                ? place.get("nationalPhoneNumber").asText()
                                : "N/A";
                        String primaryType = place.get("primaryType").asText();
                        JsonNode workingHours = place.get("regularOpeningHours").withArray("weekdayDescriptions");
                        String mapsUri = place.get("googleMapsUri").asText();
                        String websiteUri = place.has("websiteUri") && !place.get("websiteUri").isNull()
                                ? place.get("websiteUri").asText()
                                : "N/A";

                        writer.append(name).append("; ").append(address).append("; ").append(primaryType).append("; ")
                                .append(types.toString()).append("; ").append(phoneNumber).append("; ")
                                .append(workingHours.toString()).append("; ").append(mapsUri).append("; ")
                                .append(websiteUri).append("\n");

                        writer.flush();
                    }
                    System.out.println("CSV file saved: " + csvFile);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}