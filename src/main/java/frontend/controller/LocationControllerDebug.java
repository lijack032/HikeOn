package frontend.controller;

import java.util.Scanner;

public class LocationControllerDebug {

    private LocationController locationController;

    public LocationControllerDebug() {
        // Initialize the LocationController
        locationController = new LocationController();
    }

    public void debugSearchHikingSpots() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter location (or type 'exit' to quit): ");
        String location = scanner.nextLine();

        while (!location.equalsIgnoreCase("exit")) {
            // Call searchHikingSpots with the provided location
            locationController.searchHikingSpots(location);

            // Ask for another input
            System.out.println("Enter another location (or type 'exit' to quit): ");
            location = scanner.nextLine();
        }

        scanner.close();
    }

    public static void main(String[] args) {
        LocationControllerDebug debugInstance = new LocationControllerDebug();
        debugInstance.debugSearchHikingSpots();
    }
}

