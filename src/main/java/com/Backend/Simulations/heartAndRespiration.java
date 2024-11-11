import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class HeartAndRespiration {
    public static void main(String[] args) {
        int interval = 5000;
        while (true) {
            generateHeartAndRespirationData();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateHeartAndRespirationData() {
        Random random = new Random();
        double heartRate = 50 + (random.nextDouble() * 60);
        double respirationRate = 10 + (random.nextDouble() * 15);

        if (heartRate > 100 || respirationRate > 20) {
            alertPatient(heartRate, respirationRate);
        }

        if (heartRate >= 60 && heartRate <= 100 && respirationRate >= 12 && respirationRate <= 20) {
            String jsonData = String.format(
                "{ \"heartRespirationID\": %d, \"heartRate\": %.2f, \"respirationRate\": %.2f, \"timestamp\": \"%s\" }",
                random.nextInt(1000),
                heartRate,
                respirationRate,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            sendPostRequest("/heartrespirationdata", jsonData);
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

    public static void alertPatient(double heartRate, double respirationRate) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Heart Rate: " + heartRate);
        System.out.println("Respiration Rate: " + respirationRate);
    }
}
