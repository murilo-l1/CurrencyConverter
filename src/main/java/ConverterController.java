import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConverterController {

    private OkHttpClient apiClient = null;
    private ArrayList<String> currenciesList = null;

    public float convertValues(String fromCurrencyCode, String toCurrencyCode,  float amount) throws IOException, CurrencyNotFoundException{
        if(toCurrencyCode.equals(fromCurrencyCode)){
            return amount;
        }
        if(!currencyIsValid(fromCurrencyCode) || !currencyIsValid(toCurrencyCode)){
            throw new CurrencyNotFoundException(fromCurrencyCode + " or " + toCurrencyCode + "were not found in our database");
        }
            apiClient = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/currency_data/convert?to=" + toCurrencyCode + "&from=" + fromCurrencyCode + "&amount=" + amount )
                    .addHeader("apikey", getApiKey())
                    .method("GET", null)
                    .build();
            Response response = apiClient.newCall(request).execute();

            Gson gsonParser = new Gson();
            JsonObject jsonObject = gsonParser.fromJson(response.body().string(), JsonObject.class);

            float convertedValue = jsonObject.getAsJsonPrimitive("result").getAsFloat();
            return convertedValue;
    }

    private boolean currencyIsValid(String currencyCode) throws IOException{
        currenciesList = loadCurrencyList();
        return currenciesList.contains(currencyCode);
    }

    public ArrayList<String> loadCurrencyList() throws IOException{
        apiClient = new OkHttpClient().newBuilder().build();
        currenciesList = new ArrayList<>();

        Response response = fetchCurrencyList();

        try {
            //usando gson, vamos transformar as moedas do json retornado em strings legiveis
            Gson gsonParser = new Gson();
            JsonElement jsonElement = gsonParser.fromJson(response.body().charStream(), JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            for (String currency : jsonObject.getAsJsonObject("currencies").keySet()) {
                currenciesList.add(currency);
            }
        }
        finally {
            if(response != null)
                response.close();
        }
        return currenciesList;
    }

    private Response fetchCurrencyList() throws IOException {
        //enviando uma request de get para a url, para obtermos as moedas disponiveis
        Request request = new Request.Builder()
                .url("https://api.apilayer.com/currency_data/list")
                .addHeader("apikey", getApiKey())
                .method("GET", null)
                .build();

        return apiClient.newCall(request).execute();
    }

    private String getApiKey() throws IOException {
        BufferedReader reader = null;
        try {
            String filePath = "src/main/assets/apikey.txt";
            reader = new BufferedReader(new FileReader(filePath));
            String apiKey = reader.readLine();
            return apiKey;
        } catch (IOException ioe) {
            System.err.println("Error: " + ioe);
        } catch (Exception e) {
            System.err.println("Error: " + e);
        } finally {
            try {
                if(reader != null) reader.close();
            }catch (NullPointerException e){
                System.err.println("Error: " + e);
            }
        }
        return null;
    }
}