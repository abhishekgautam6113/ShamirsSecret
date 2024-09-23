import org.json.JSONObject;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PolynomialCoefficientFinder {

    public static void main(String[] args) {
        // Read JSON from file
        String jsonInput = readJsonFile("testcase1.json");
        JSONObject jsonObject = new JSONObject(jsonInput);

        // Extract keys
        JSONObject keys = jsonObject.getJSONObject("keys");
        int n = keys.getInt("n");
        int k = keys.getInt("k");

        System.out.println("n: " + n);
        System.out.println("k: " + k);

        // Extracting values based on keys
        for (int i = 1; i <= n; i++) {
            if (jsonObject.has(String.valueOf(i))) {
                JSONObject item = jsonObject.getJSONObject(String.valueOf(i));
                String base = item.getString("base");
                String value = item.getString("value");
                System.out.println("Key: " + i + ", Base: " + base + ", Value: " + value);
            }
        }
    }

    private static String readJsonFile(String filePath) {
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                contentBuilder.append(currentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
