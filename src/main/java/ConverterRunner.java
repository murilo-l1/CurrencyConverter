import java.io.IOException;
import java.util.ArrayList;

public class ConverterRunner {
    public static void main(String[] args) {
        ConverterController converterController = new ConverterController();
        try{
            float x;
            x = converterController.convertValues("btc", "usd", 1);
            System.out.println(x);
        }
        catch (CurrencyNotFoundException ce){
        System.out.println(ce.getMessage());
    }
        catch (IOException ioe)
    {
        System.out.println(ioe.getMessage());
    }

    }
}
