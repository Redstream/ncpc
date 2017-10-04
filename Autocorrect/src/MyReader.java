import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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

            String[] firstLine = readWords.get(0).split("[\\s]");       //Split on space
            int nrOfWordsInDictionary = Integer.parseInt(firstLine[0]);
            int nrOfWordsToType = Integer.parseInt(firstLine[0]);
            readWords.remove(0);                                        //Remove when info has been retrieved

            System.out.println("Words in dictionary: " + nrOfWordsInDictionary);
            System.out.println("Words to type: " + nrOfWordsToType);

            System.out.println("\nIn dictionary:");

            this.wordsInDictionary = new ArrayList<>();
            int i;
            for(i = 0; i < nrOfWordsInDictionary; i++) {
                System.out.println(readWords.get(i));
                wordsInDictionary.add(readWords.get(i));
            }

            System.out.println("\nTo type:");
            this.wordsToType = new ArrayList<>();
            for(int j = i; j < (nrOfWordsInDictionary+nrOfWordsToType); j++) {
                System.out.println(readWords.get(j));
                wordsToType.add(readWords.get(j));
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}
