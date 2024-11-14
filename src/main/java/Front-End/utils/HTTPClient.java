package frontend.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPClient {

    // Method to send a GET request to the API
    public static String sendGetRequest(String urlString) {
        StringBuilder response = new StringBuilder();
        try {
            // Create a URL object from the string
            URL url = new URL(new URI(urlString).toASCIIString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Timeout after 5 seconds
            connection.setReadTimeout(5000);    // Timeout while reading data

            // Get the response code
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 200 OK
                // Read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            } else {
                System.out.println("GET request failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.toString(); // Return the API response as a string
    }
}

