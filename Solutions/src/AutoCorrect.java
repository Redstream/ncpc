import IO.Reader;
import IO.Writer;

import java.util.List;

public class AutoCorrect {
    public static void main(String[] args) {
        List<String> input = new Reader().readInput();
        new Writer().writeOutput(input);
    }
}
