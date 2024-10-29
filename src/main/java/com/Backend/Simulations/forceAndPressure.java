import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class forceAndPressure {
    public static void main(String[] args) {
        int interval = 5000;  // Interval between data sends in milliseconds
        while (true) {
            generateForcePressureData();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void generateForcePressureData() {
        Random random = new Random();
        double pressureDistribution = 1 + (random.nextDouble() * 9.0);
        double positionTime = 0.5 + (random.nextDouble() * 9.5);
        boolean muscleActivation = random.nextBoolean();

        if (pressureDistribution > 8 || positionTime > 10) {
            alertPatient(pressureDistribution, positionTime);
        }

        if (pressureDistribution >= 1 && pressureDistribution <= 8 && positionTime >= 1 && positionTime <= 10) {
            String jsonData = String.format(
                "{ \"forcePressureID\": %d, \"exerciseID\": %d, \"muscleActivation\": %b, \"pressureDistribution\": %.2f, \"positionTime\": %.2f, \"timestamp\": \"%s\" }",
                random.nextInt(1000),
                random.nextInt(10) + 1,
                muscleActivation,
                pressureDistribution,
                positionTime,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)
            );
            sendPostRequest("/forcepressuredata", jsonData);
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

    public static void alertPatient(double pressureDistribution, double positionTime) {
        System.out.println("ALERT: Threshold exceeded!");
        System.out.println("Pressure Distribution: " + pressureDistribution);
        System.out.println("Position Time: " + positionTime);
    }
}
