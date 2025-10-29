package pro.kensait.berrybooks.api;

import pro.kensait.berrybooks.model.CustomerStats;
import pro.kensait.berrybooks.model.CustomerTO;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Berry Books REST API??????
 */
public class BerryBooksApiClient {
    private final String baseUrl;

    public BerryBooksApiClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * ???????????
     */
    public List<CustomerStats> getAllCustomers() throws Exception {
        URL url = new URL(baseUrl + "/customers");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP GET failed: " + responseCode);
        }

        BufferedReader in = new BufferedReader(
            new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();

        JSONArray jsonArray = new JSONArray(response.toString());
        List<CustomerStats> customers = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            CustomerStats stats = new CustomerStats();
            stats.setCustomerId(json.getLong("customerId"));
            stats.setCustomerName(json.getString("customerName"));
            stats.setEmail(json.getString("email"));
            stats.setBirthDate(LocalDate.parse(json.getString("birthDate")));
            stats.setAddress(json.getString("address"));
            stats.setOrderCount(json.getLong("orderCount"));
            stats.setBookCount(json.getLong("bookCount"));
            customers.add(stats);
        }

        return customers;
    }

    /**
     * ???????
     */
    public void updateCustomer(Long customerId, CustomerTO customerTO) throws Exception {
        URL url = new URL(baseUrl + "/customers/" + customerId);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("PUT");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setDoOutput(true);

        JSONObject json = new JSONObject();
        json.put("customerName", customerTO.getCustomerName());
        json.put("email", customerTO.getEmail());
        json.put("birthDate", customerTO.getBirthDate());
        json.put("address", customerTO.getAddress());

        try (OutputStream os = conn.getOutputStream()) {
            byte[] input = json.toString().getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            // ?????????????
            BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getErrorStream(), StandardCharsets.UTF_8));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                response.append(line);
            }
            in.close();
            
            // JSON????????????
            try {
                JSONObject errorJson = new JSONObject(response.toString());
                String message = errorJson.optString("message", "?????????");
                throw new RuntimeException(message);
            } catch (Exception e) {
                throw new RuntimeException("HTTP PUT failed: " + responseCode);
            }
        }
    }
}
