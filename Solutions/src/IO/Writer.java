package IO;

import java.io.File;
import java.util.List;

public class Writer {

    public void writeOutput(List<String> lines) {
        lines.forEach(System.out::println);
    }

    public void writeOutput(String line) {
        System.out.println(line);
    }

    public void fileExists(String filename) {
        boolean exists = new File(filename).exists();
    }

}
