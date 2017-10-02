package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {

    private ArrayList<String> contents = new ArrayList<>();

    public ArrayList<String> readFile(String filename) {
        try {
            return read(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            contents = new ArrayList<>();
            System.out.println("File does not exist: " + filename + "\n" + ex);
            return contents;
        }
    }

    public ArrayList<String> readInput () {
        return read(new InputStreamReader(System.in));
    }

    public ArrayList<String> getLastRead() {
        return contents;
    }

    private ArrayList<String> read (InputStreamReader in) {
        contents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(in)) {
            String line;
            while ((line = br.readLine()) != null) {
                contents.add(line);
            }
        } catch (IOException e) {
            System.out.println("Could not read input \n" + e);
        }
        return contents;
    }
}
