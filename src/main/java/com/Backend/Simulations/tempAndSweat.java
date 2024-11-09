import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class tempAndSweat {
    public static void print(String[] args) {
        int num = 10;  // Specify the number of records to generate
        generateTempSweatData(num);
    }

    public static int generateTempSweatData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity

        int count = 0;
        Random random = new Random();

        for (int i = 0; i < num; i++) {
            double temperature = 35 + (random.nextDouble() * 5);  // Temperature in range [35, 40]
            double sweatRate = random.nextDouble();  // Sweat rate in range [0, 1]

            // Check if the data exceeds a certain threshold and alert the patient
            if (temperature > 38 || sweatRate > 1.0) {
                alertPatient(temperature, sweatRate);
            }

            // Apply thresholds for testing
            if (temperature >= 36 && temperature <= 38 && sweatRate >= 0.1 && sweatRate <= 1.0) {
                String jsonData = String.format(
                    "{ \"tempSweatID\": %d, \"temperature\": %.2f, \"sweatRate\": %.2f, \"timestamp\": \"%s\" }",
                    i + 1,
                    temperature,
                    sweatRate,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  // Current timestamp
                );

                try {
                    // Send POST request to the database
                    URL url = new URL(System.getenv("DB_URL") + "/tempsweatdata"); // Db URL has a place holder in it as of right now
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
    public static void alertPatient(double temperature, double sweatRate) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Temperature: " + temperature);
        System.out.println("Sweat Rate: " + sweatRate);
        // You can implement additional alerting mechanisms such as sending an email or notification
    }
}
