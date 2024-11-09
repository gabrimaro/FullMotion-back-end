import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class heartAndRespiration {
    public static void print(String[] args) {
        int num = 10;  // Specify the number of records to generate
        generateHeartAndRespirationData(num);
    }

    public static int generateHeartAndRespirationData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity

        int count = 0;
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            double heartRate = 50 + (random.nextDouble() * 60);  // Heart rate in range [50, 110]
            double respirationRate = 10 + (random.nextDouble() * 15);  // Respiration rate in range [10, 25]

            // Check if the data exceeds a certain threshold and alert the patient
            if (heartRate > 100 || respirationRate > 20) {
                alertPatient(heartRate, respirationRate);
            }

            // Apply thresholds for testing
            if (heartRate >= 60 && heartRate <= 100 && respirationRate >= 12 && respirationRate <= 20) {
                String jsonData = String.format(
                    "{ \"heartRespirationID\": %d, \"heartRate\": %.2f, \"respirationRate\": %.2f, \"timestamp\": \"%s\" }",
                    i + 1,
                    heartRate,
                    respirationRate,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  // Current timestamp
                );

                try {
                    // Send POST request to the database
                    URL url = new URL(System.getenv("DB_URL") + "/heartrespirationdata"); // Db URL has a place holder in it as of right now
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
    public static void alertPatient(double heartRate, double respirationRate) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Heart Rate: " + heartRate);
        System.out.println("Respiration Rate: " + respirationRate);
        // You can implement additional alerting mechanisms such as sending an email or notification
    }
}
