package com.Backend.Simulations;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class forceAndPressure {

    public static void main(String[] args) {
        int intervalMillis = 1000;  // Set interval to 1 second
        int durationSeconds = 60;   // Run for 60 seconds

        int successfulRequests = startContinuousForcePressureStream(intervalMillis, durationSeconds);
        System.out.println("Streaming Complete: ");
        System.out.println("Total records sent: " + successfulRequests);
    }

    public static int startContinuousForcePressureStream(int intervalMillis, int durationSeconds) {
        int count = 0;
        long endTime = System.currentTimeMillis() + durationSeconds * 1000;

        while (System.currentTimeMillis() < endTime) {
            
            double pressureDistribution = 1 + 4 * Math.sin(Math.toRadians(count * 10)) + 4; // Sinusoidal wave [1, 9]
            double positionTime = 0.5 + 4.5 * Math.cos(Math.toRadians(count * 15)) + 4.5; // Cosine wave [0.5, 10]
            boolean muscleActivation = count % 2 == 0; // Alternating between true and false
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);

            // Alert if values exceed thresholds
            if (pressureDistribution > 8 || positionTime > 9.5) {
                alertPatient(pressureDistribution, positionTime);
            }

            // Create JSON data
            String jsonData = String.format(
                "{ \"forcePressureID\": %d, \"exerciseID\": %d, \"muscleActivation\": %b, \"pressureDistribution\": %.2f, \"positionTime\": %.2f, \"timestamp\": \"%s\" }",
                count + 1,
                (count % 10) + 1, // Cyclical pattern for exerciseID [1, 10]
                muscleActivation,
                pressureDistribution,
                positionTime,
                timestamp
            );

            if (sendData(jsonData)) {
                count++;
            }

            try {
                Thread.sleep(intervalMillis);  // Wait for the specified interval
            } catch (InterruptedException e) {
                System.out.println("Streaming interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }

        return count;
    }

    private static boolean sendData(String jsonData) {
        try {
            URL url = new URL(System.getenv("DB_URL") + "/forcepressuredata");
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
                System.out.println("Successfully sent record: " + jsonData);
                return true;
            } else {
                System.out.println("Failed to send record. Response code: " + responseCode);
                return false;
            }

        } catch (Exception e) {
            System.out.println("Error sending record.");
            e.printStackTrace();
            return false;
        }
    }

    // Method to alert the patient when a threshold is exceeded
    public static void alertPatient(double pressureDistribution, double positionTime) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Pressure Distribution: " + pressureDistribution);
        System.out.println("Position Time: " + positionTime);
    }
}
