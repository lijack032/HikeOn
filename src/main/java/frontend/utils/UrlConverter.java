package frontend.utils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class UrlConverter {

    /**
     * Converts a string into a valid URL.
     *
     * @param urlString The input string representing the URL.
     * @return A valid URL object, or null if the input string cannot be converted.
     */
    public static URL convertToUrl(String urlString) {
        try {
            // Replace spaces and ensure proper encoding
            final URI uri = new URI(urlString.replace(" ", "%20"));

            // Convert the URI to a URL
            return uri.toURL();
        }
        catch (URISyntaxException | MalformedURLException exception) {
            System.err.println("Invalid URL syntax: " + urlString + " - " + exception.getMessage());
            return null;
        }
    }

}

