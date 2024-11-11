import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class RepsAndDuration {
    public static void main(String[] args) {
        int interval = 5000;
        while (true) {
            generateRepsAndDurationData();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateRepsAndDurationData() {
        Random random = new Random();
        int reps = random.nextInt(20) + 1;
        int duration = random.nextInt(600) + 10;

        if (reps > 15 || duration > 300) {
            alertPatient(reps, duration);
        }

        if (reps >= 5 && reps <= 15 && duration >= 30 && duration <= 300) {
            String jsonData = String.format(
                "{ \"repsDurationID\": %d, \"reps\": %d, \"duration\": %d, \"timestamp\": \"%s\" }",
                random.nextInt(1000),
                reps,
                duration,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            sendPostRequest("/repsdurationdata", jsonData);
        }
    }

    public static void sendPostRequest(String endpoint, String jsonData) {
        try {
            URL url = new URL(System.getenv("DB_URL") + endpoint);
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
                System.out.println("Data sent successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void alertPatient(int reps, int duration) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Reps: " + reps);
        System.out.println("Duration: " + duration);
    }
}
