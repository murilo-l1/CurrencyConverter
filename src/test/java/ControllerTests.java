import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {

    @Test
    void shouldReturnTrueIfACurrencyExistsInTheList() throws IOException, CurrencyNotFoundException{
        var convertController = new ConverterController();
        assertTrue(convertController.currencyIsValid("EUR"));
        assertTrue(convertController.currencyIsValid("BRL"));
    }

    @Test
    void shouldThrowsAnCurrencyNotFoundExceptionWhenCurrencyIsWrong() {
        var convertController = new ConverterController();
        assertThrows(CurrencyNotFoundException.class, () -> convertController.currencyIsValid("01"));
    }

    @Test
    void assertThatSameCurrenciesHaveTheSameValue() throws  IOException, CurrencyNotFoundException{
        var controller = new ConverterController();
        assertEquals( 1f, controller.convertValues("USD", "USD", 1));
    }


}