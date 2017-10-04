import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class MyReader {

    Path fileName;
    List<String> wordsInDictionary;
    List<String> wordsToType;

    public MyReader(Path fileName) {
        this.fileName = fileName;
    }

    public void read() {
        try {
            List<String> readWords = Files.readAllLines(this.fileName);
            for(String s : readWords) {
                System.out.println(s);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}
