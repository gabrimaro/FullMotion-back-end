import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class forceAndPressure {
    public static void print(String[] args) {
        int num = 10;  // Specify the number of records to generate
        generateForcePressureData(num);
    }

    public static int generateForcePressureData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity

        int count = 0;
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            double pressureDistribution = 1 + (random.nextDouble() * 9.0);  // Pressure distribution in range [1, 10]
            double positionTime = 0.5 + (random.nextDouble() * 9.5);  // Position time in range [0.5, 10]
            boolean muscleActivation = random.nextBoolean();  // Random boolean for muscle activation

            // Check if the data exceeds a certain threshold and alert the patient
            if (pressureDistribution > 8 || positionTime > 10) {
                alertPatient(pressureDistribution, positionTime);
            }

            // Apply thresholds for testing
            if (pressureDistribution >= 1 && pressureDistribution <= 8 && positionTime >= 1 && positionTime <= 10) {
                String jsonData = String.format(
                    "{ \"forcePressureID\": %d, \"exerciseID\": %d, \"muscleActivation\": %b, \"pressureDistribution\": %.2f, \"positionTime\": %.2f, \"timestamp\": \"%s\" }",
                    i + 1,
                    random.nextInt(10) + 1,  // Random exercise ID as integer
                    muscleActivation,
                    pressureDistribution,
                    positionTime,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  // Current timestamp
                );

                try {
                    // Send POST request to the database
                    URL url = new URL(System.getenv("DB_URL") + "/forcepressuredata"); // Db URL has a place holder in it as of right now
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
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return count;
    }

    // Method to alert the patient when a threshold is exceeded
    public static void alertPatient(double pressureDistribution, double positionTime) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Pressure Distribution: " + pressureDistribution);
        System.out.println("Position Time: " + positionTime);
        // Here, you can implement additional alerting mechanisms such as sending an email or notification
    }
}
