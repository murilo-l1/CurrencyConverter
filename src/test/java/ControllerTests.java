import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {

    @Test
    void shouldReturnAValidListOfCurrencies() throws IOException{
        var controller = new ConverterController();
        assertNotNull(controller.loadCurrencyList());
    }

    @Test
    void assertThatSameCurrenciesHaveTheSameValue() throws IOException, CurrencyNotFoundException{
        var controller = new ConverterController();
        assertEquals( 1f, controller.convertValues("USD", "USD", 1));
    }

    @Test
    void shouldReturnValidConversionResult() throws IOException, CurrencyNotFoundException{
        var controller = new ConverterController();
        assertNotNull(controller.convertValues("USD","EUR",10));
    }

    @Test
    void shouldThrowCurrencyNotFoundExceptionWhenCurrencyCodeIsNotValid() throws IOException {
        var controller = new ConverterController();
        assertThrows(CurrencyNotFoundException.class, () -> controller.convertValues("USD", "XYZ", 1));
    }

}