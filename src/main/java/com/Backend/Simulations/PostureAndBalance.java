package com.Backend.Simulations;

import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class PostureAndBalance {

    private static final DecimalFormat df = new DecimalFormat("#.##");
    private static final SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static final List<String> bodyAlignmentOptions = Arrays.asList(
        "Neutral", "Forward", "Backward", "Left Tilt", "Right Tilt"
    );
    private static final List<String> centerOfMassOptions = Arrays.asList(
        "Center", "Forward Shift", "Backward Shift", "Left Shift", "Right Shift"
    );
    private static final List<String> swayPatternOptions = Arrays.asList(
        "Stable", "Mild Sway", "Moderate Sway", "Significant Sway"
    );

    public static void main(String[] args) {
        int intervalMillis = 1000;  // Set interval to 1 second
        int durationSeconds = 60;   // Run for 60 seconds

        int successfulRequests = startContinuousDataStream(intervalMillis, durationSeconds);
        System.out.println("Streaming Complete: ");
        System.out.println("Total records sent: " + successfulRequests);
    }

    public static int startContinuousDataStream(int intervalMillis, int durationSeconds) {
        Random random = new Random();
        int count = 0;
        long endTime = System.currentTimeMillis() + durationSeconds * 1000;

        while (System.currentTimeMillis() < endTime) {
            String timestamp = timestampFormat.format(new Date());
            String jsonData = String.format(
                "{ \"timestamp\": \"%s\", \"exerciseID\": %d, \"bodyAlignment\": \"%s\", \"centerOfMass\": \"%s\", \"swayPattern\": \"%s\", \"balanceTime\": %s, \"supportNeeded\": %b }",
                timestamp,
                count + 1,
                random.nextInt(100) + 1,
                bodyAlignmentOptions.get(random.nextInt(bodyAlignmentOptions.size())),
                centerOfMassOptions.get(random.nextInt(centerOfMassOptions.size())),
                swayPatternOptions.get(random.nextInt(swayPatternOptions.size())),
                df.format(5 + (55 * random.nextDouble())),
                random.nextBoolean()
            );

            if (sendData(jsonData)) {
                count++;
            }

            try {
                Thread.sleep(intervalMillis);  // Wait for the specified interval before sending the next record
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
            URL url = new URL(System.getenv("DB_URL") + "/balancedata");
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
                System.out.println("Successfully sent record at " + jsonData);
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
}