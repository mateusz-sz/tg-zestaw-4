import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WordsFilterTest {

    private WordsFilter wf = new WordsFilter();
    
    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFirstArgumentIsNull() {
        wf = new WordsFilter();
        wf.filter(null, "Abba");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenSecondArgumentIsNull() {
        wf.filter(Arrays.asList("Alfa", "Beta"), null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenBothArgumentsAreNull() {
        wf.filter(null, null);
    }

    @Test
    public void shouldReturnFilteredList() {
        List<String> list = Arrays.asList("Alfa", "Beta", "Gamma", null, "Delta");

        List<String> result = wf.filter(list, "Alfa");

        assertEquals(
                Arrays.asList("Beta", "Gamma", null, "Delta"),
                result
                );
    }
}




















