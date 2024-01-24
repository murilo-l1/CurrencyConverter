public class CurrencyNotFoundException extends Exception{
    public CurrencyNotFoundException() {
        super("This currency couldnt be found in our database");
    }
}