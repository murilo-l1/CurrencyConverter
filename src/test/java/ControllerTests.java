import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTests {

    @Test
    void shouldReturnIfACurrencyExistsInTheList() throws IOException {
        var convertController = new ConverterController();
        boolean t = convertController.currencyIsValid("USD");
        assertEquals(true, t);
    }


}