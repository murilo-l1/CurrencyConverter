import java.io.IOException;
import java.util.ArrayList;

public class ConverterRunner {
    public static void main(String[] args) {
        ConverterController converterController = new ConverterController();
        try{
            float x;
            x = converterController.convertValues("usd", "btc", 1);
            System.out.println(x);

        }catch (IOException ioe){

        }catch (CurrencyNotFoundException ce){

        }


    }
}
