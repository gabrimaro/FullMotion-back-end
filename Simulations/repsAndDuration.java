import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class repsAndDuration {
    public static void main(String[] args) {
        int num = 10;  // Specify the number of records to generate
        generateRepsAndDurationData(num);
    }

    public static int generateRepsAndDurationData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity

        int count = 0;
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            int reps = random.nextInt(20) + 1;  // Reps in range [1, 20]
            int duration = random.nextInt(600) + 10;  // Duration in range [10, 610]

            // Check if the data exceeds a certain threshold and alert the patient
            if (reps > 15 || duration > 300) {
                alertPatient(reps, duration);
            }

            // Apply thresholds for testing
            if (reps >= 5 && reps <= 15 && duration >= 30 && duration <= 300) {
                String jsonData = String.format(
                    "{ \"repsDurationID\": %d, \"reps\": %d, \"duration\": %d, \"timestamp\": \"%s\" }",
                    i + 1,
                    reps,
                    duration,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  // Current timestamp
                );

                try {
                    // Send POST request to the database
                    URL url = new URL(System.getenv("DB_URL") + "/repsdurationdata");
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
    public static void alertPatient(int reps, int duration) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Reps: " + reps);
        System.out.println("Duration: " + duration);
        // You can implement additional alerting mechanisms such as sending an email or notification
    }
}
