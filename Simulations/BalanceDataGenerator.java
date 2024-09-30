import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BalanceDataGenerator {

    private static final DecimalFormat df = new DecimalFormat("#.##");

    // Example set of possible values for body alignment, center of mass, and sway patterns
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
        int num = 10;  // Specify the number of records to generate
        generateBalanceData(num);
    }

    public static int generateBalanceData(int num) {
        if (num < 1) num = 1;
        if (num > 146) num = 146;  // Limiting the number of records for simplicity
        
        int count = 0;
        Random random = new Random();

        for (int x = 0; x < num; x++) {
            String jsonData = String.format(
                "{ \"balanceID\": %d, \"sessionID\": %d, \"bodyAlignment\": \"%s\", \"centerOfMass\": \"%s\", \"swayPattern\": \"%s\", \"balanceTime\": %s, \"supportNeeded\": %b }",
                x + 1, 
                random.nextInt(100) + 1,  // Random session ID as an integer
                bodyAlignmentOptions.get(random.nextInt(bodyAlignmentOptions.size())),  // Random body alignment
                centerOfMassOptions.get(random.nextInt(centerOfMassOptions.size())),  // Random center of mass
                swayPatternOptions.get(random.nextInt(swayPatternOptions.size())),  // Random sway pattern
                df.format(5 + (55 * random.nextDouble())),  // Balance time in seconds
                random.nextBoolean()  // Boolean for support needed
            );

            try {
                // Send POST request to the database
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
                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return count;
    }
}