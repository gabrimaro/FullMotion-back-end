package com.Backend.Simulations;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MotionDataGenerator {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void print(String[] args) {
        int num = 10;  // Specify the number of records to generate
        int successfulRequests = generateMotionData(num);
        System.out.println("Report: ");
        System.out.println("Total records generated: " + num);
        System.out.println("Successfully sent records: " + successfulRequests);
        System.out.println("Failed records: " + (num - successfulRequests));
    }

    public static int generateMotionData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity

        int count = 0;

        for (int x = 0; x < num; x++) {
            
            double exerciseID = 1 + (x % 10); // Cyclical pattern from 1 to 10
            double jointAngle = 90 + 90 * Math.sin(Math.toRadians(x * 10)); // Sinusoidal wave between 0 and 180
            double rangeOfMotion = 10 + (x * 5) % 170; // Linearly increasing with a modulo for bounds
            double movementSpeed = 0.1 + 2.4 * Math.pow(Math.sin(Math.toRadians(x * 5)), 2); // Parabolic curve
            double stepLength = 0.3 + Math.log10(1 + x % 10) * 0.3; // Logarithmic increase
            double cadence = 60 + 30 * Math.sin(Math.toRadians(x * 15)) + (x % 10); // Linear and sinusoidal combination

            String jsonData = String.format(
                "{ \"trackingID\": %d, \"exerciseID\": %s, \"jointAngle\": %s, \"rangeOfMotion\": %s, \"movementSpeed\": %s, \"stepLength\": %s, \"cadence\": %s, \"timestamp\": \"%s\" }",
                x + 1, 
                df.format(exerciseID), 
                df.format(jointAngle),  
                df.format(rangeOfMotion), 
                df.format(movementSpeed),  
                df.format(stepLength),  
                df.format(cadence),  
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  
            );

            System.out.println("Generated Record: " + jsonData);  // Print each generated record

            try {
                // Send POST request to the database
                URL url = new URL(System.getenv("DB_URL") + "/motiondata"); // Db URL has a place holder in it as of right now
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; utf-8");
                conn.setDoOutput(true);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_CREATED) {
                    count++;
                    System.out.println("Successfully sent record " + (x + 1));
                } else {
                    System.out.println("Failed to send record " + (x + 1) + ". Response code: " + responseCode);
                }

            } catch (Exception e) {
                System.out.println("Error sending record " + (x + 1));
                e.printStackTrace();
            }
        }

        return count;
    }

    // Method to send alerts (example implementation)
    private static void sendAlert(String message) {
        try {
            URL alertUrl = new URL(System.getenv("ALERT_URL")); // Ensure ALERT_URL is set in your environment
            HttpURLConnection alertConn = (HttpURLConnection) alertUrl.openConnection();
            alertConn.setRequestMethod("POST");
            alertConn.setRequestProperty("Content-Type", "application/json; utf-8");
            alertConn.setDoOutput(true);

            String alertData = String.format("{\"alert\": \"%s\"}", message);
            try (OutputStream os = alertConn.getOutputStream()) {
                byte[] input = alertData.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int alertResponseCode = alertConn.getResponseCode();
            if (alertResponseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Alert sent: " + message);
            } else {
                System.out.println("Failed to send alert: " + message);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}