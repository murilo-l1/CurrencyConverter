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

    public float convertValues(String toCurrencyCode, String fromCurrencyCode, float amount) throws IOException, CurrencyNotFoundException{
        String toCurrency = formatCurrencyCode(toCurrencyCode);
        String fromCurrency = formatCurrencyCode(fromCurrencyCode);

        if(toCurrency.equals(fromCurrency)){
            return 1.0f;
        }

        if(currencyIsValid(toCurrency) && currencyIsValid(fromCurrency)){

            apiClient = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.apilayer.com/currency_data/convert?to=" + toCurrency + "&from=" + fromCurrency + "&amount=" + amount )
                    .addHeader("apikey", getApiKey())
                    .method("GET", null)
                    .build();
            Response response = apiClient.newCall(request).execute();
            //System.out.println(response.body().string());

            Gson gsonParser = new Gson();

            JsonObject jsonObject = gsonParser.fromJson(response.body().string(), JsonObject.class);

            float convertedValue = jsonObject.getAsJsonPrimitive("result").getAsFloat();

            return convertedValue;
        }else {
            throw new CurrencyNotFoundException();
        }
    }

    private String formatCurrencyCode(String currencyCode){
        return currencyCode.replaceAll(" ", "").toUpperCase();
    }


    private ArrayList<String> loadCurrencyList() throws IOException{
        apiClient = new OkHttpClient().newBuilder().build();
        currenciesList = new ArrayList<>();

        //enviando uma request de get para a url, para obtermos as moedas disponiveis
        Request request = new Request.Builder()
                .url("https://api.apilayer.com/currency_data/list")
                .addHeader("apikey", getApiKey())
                .method("GET", null)
                .build();

        Response response = apiClient.newCall(request).execute();

        //usando gson, vamos transformar as moedas do json retornado em strings legiveis
        Gson gsonParser = new Gson();
        JsonElement jsonElement = gsonParser.fromJson(response.body().charStream(), JsonElement.class);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        for(String currency : jsonObject.getAsJsonObject("currencies").keySet()){
            currenciesList.add(currency);
        }

        return currenciesList;
    }

    public boolean currencyIsValid(String currency) throws IOException{

        currenciesList = loadCurrencyList();
        for(String validCurrency : currenciesList){
            if(validCurrency.equals(currency))
                return true;
        }
        return false;
    }

    private String getApiKey() throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("C:\\Users\\muril\\IdeaProjects\\CurrencyConverter\\src\\main\\assets\\apikey.txt"));
            String apiKey = reader.readLine();
            return apiKey;
        } catch (IOException ioe) {
            System.out.println("Error: " + ioe);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            try {
                if(reader != null) reader.close();
            }catch (NullPointerException e){
                System.out.println("Error: " + e);
            }
        }
        return null;
    }
}