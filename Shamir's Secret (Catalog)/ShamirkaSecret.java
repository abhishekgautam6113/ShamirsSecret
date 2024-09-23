import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ShamirkaSecret {

    // Function to decode a value from a specific base to base 10
    public static long decodeValue(String value, int base) {
        return Long.parseLong(value, base);  // Use Long to handle large numbers
    }

    // Function to calculate Lagrange Interpolation and return the constant term (c)
    public static long lagrangeInterpolation(List<long[]> points, int k) {
        long c = 0;

        for (int i = 0; i < k; i++) {
            long term = points.get(i)[1];  // y_i
            for (int j = 0; j < k; j++) {
                if (i != j) {
                    term *= (0 - points.get(j)[0]); // Update the term for Lagrange
                    term /= (points.get(i)[0] - points.get(j)[0]); // Keep division here for accurate integer result
                }
            }
            c += term;
        }
        return c; // Return long instead of double
    }

    // Function to load JSON from file
    public static String loadJSONFromFile(String filename) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void main(String[] args) {
        // Load JSON data from files for both test cases
        String firstTestCaseFile = "testcase1.json";  // Change this to your actual file path
        String secondTestCaseFile = "testcase2.json"; // Change this to your actual file path

        // Load first test case JSON
        String inputJson1 = loadJSONFromFile(firstTestCaseFile);
        processTestCase(inputJson1);

        // Load second test case JSON
        String inputJson2 = loadJSONFromFile(secondTestCaseFile);
        processTestCase(inputJson2);
    }

    // Function to process each test case
    public static void processTestCase(String inputJson) {
        // Parse the JSON
        JSONObject json = new JSONObject(inputJson);
        JSONObject keys = json.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        // Collect the points (x, y) pairs
        List<long[]> points = new ArrayList<>();

        for (String key : json.keySet()) {
            if (!key.equals("keys")) {
                int x = Integer.parseInt(key);  // x is the key
                JSONObject point = json.getJSONObject(key);
                int base = Integer.parseInt(point.getString("base"));
                String value = point.getString("value");
                long y = decodeValue(value, base);  // y is the decoded value using Long
                points.add(new long[]{x, y});
            }
        }

        // Find the constant term 'c' using Lagrange Interpolation
        long constantTerm = lagrangeInterpolation(points, k);
        System.out.println("The constant term 'c' is: " + constantTerm);
    }
}
