import java.util.List;
import java.util.stream.Collectors;

public class WordsFilter {
    public List<String> filter(List<String> l, String s) {
        if (l == null) {
            throw new IllegalArgumentException("List<String> l must not be a null!");
        }
        if (s == null) {
            throw new IllegalArgumentException("String s must not be a null!");
        }

        return l.stream()
                .filter(word -> word == null || !word.equals(s))
                .collect(Collectors.toList());
    }
}
