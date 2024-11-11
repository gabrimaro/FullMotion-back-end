import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class TempAndSweat {
    public static void main(String[] args) {
        int interval = 5000;
        while (true) {
            generateTempSweatData();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateTempSweatData() {
        Random random = new Random();
        double temperature = 35 + (random.nextDouble() * 5);
        double sweatRate = random.nextDouble();

        if (temperature > 38 || sweatRate > 1.0) {
            alertPatient(temperature, sweatRate);
        }

        if (temperature >= 36 && temperature <= 38 && sweatRate >= 0.1 && sweatRate <= 1.0) {
            String jsonData = String.format(
                "{ \"tempSweatID\": %d, \"temperature\": %.2f, \"sweatRate\": %.2f, \"timestamp\": \"%s\" }",
                random.nextInt(1000),
                temperature,
                sweatRate,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            sendPostRequest("/tempsweatdata", jsonData);
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

    public static void alertPatient(double temperature, double sweatRate) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Temperature: " + temperature);
        System.out.println("Sweat Rate: " + sweatRate);
    }
}
