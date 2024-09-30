import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class MotionDataGenerator {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    public static void main(String[] args) {
        int num = 10;  // Specify the number of records to generate
        generateMotionData(num);
    }

    public static int generateMotionData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity
        
        int count = 0;
        Random random = new Random();

        for (int x = 0; x < num; x++) {
            String jsonData = String.format(
                "{ \"trackingID\": %d, \"exerciseID\": %s, \"jointAngle\": %s, \"rangeOfMotion\": %s, \"movementSpeed\": %s, \"stepLength\": %s, \"cadence\": %s, \"timestamp\": \"%s\" }",
                x + 1, 
                df.format(1 + (9 * random.nextDouble())),  // Random exercise ID as float
                df.format(180 * random.nextDouble()),  // Joint angle in degrees
                df.format(10 + (170 * random.nextDouble())),  // Range of motion in degrees
                df.format(0.1 + (2.4 * random.nextDouble())),  // Speed in meters per second
                df.format(0.3 + (1.2 * random.nextDouble())),  // Step length in meters
                df.format(60 + (90 * random.nextDouble())),  // Cadence (steps per minute)
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)  // Current timestamp
            );

            try {
                // Send POST request to the database
                URL url = new URL(System.getenv("DB_URL") + "/motiondata");
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

        return count;
    }
}