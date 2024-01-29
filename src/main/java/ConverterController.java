import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ConverterController {

    private OkHttpClient apiClient = null;
    private ArrayList<String> currenciesList = null;

    public float convertValues(String fromCurrencyCode, String toCurrencyCode,  float amount) throws IOException, CurrencyNotFoundException{

        String fromCurrency = formatCurrencyCode(fromCurrencyCode);
        String toCurrency = formatCurrencyCode(toCurrencyCode);

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

    public boolean currencyIsValid(String currency) throws IOException, CurrencyNotFoundException{
        try {
            currenciesList = loadCurrencyList();
            boolean status = binarySearch(currency);
            if(status == true){
                return true;
            }else {
                throw new CurrencyNotFoundException();
            }
        }
        finally {
            //limpando a lista após encontrar a moeda ou jogar a excessão
            if(currenciesList != null)
                currenciesList.clear();
        }
    }

    private boolean binarySearch(String currency) throws IOException{
        int inicio = 0;
        int fim = loadCurrencyList().size() - 1;
        while(inicio <= fim){
            int meio = (inicio + fim) / 2;
            String midCurrency = currenciesList.get(meio);

            int comparison = midCurrency.compareTo(currency);

            if(comparison == 0){
                return true;
            }
            else if(comparison < 0){
                inicio = meio + 1;
            }
            else  {
                fim = meio - 1;
            }
        }
        return false;
    }

    //TODO: deixar isso aqui mais eficaz - abstraindo o fileName para um Path
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