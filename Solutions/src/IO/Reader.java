package IO;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Reader {
    private ArrayList<String> contents = new ArrayList<>();
    private String line = "";

    /**
     * Read a whole file into an ArrayList<String>
     * @param filename
     * @return
     */
    public ArrayList<String> readFile(String filename) {
        try {
            return read(new FileReader(filename));
        } catch (FileNotFoundException ex) {
            contents = new ArrayList<>();
            System.out.println("File does not exist: " + filename + "\n" + ex);
            return contents;
        }
    }

    /**
     * Reads from System.in until it gets an empty line.
     * @return
     */
    public ArrayList<String> readInput () {
        return read(new InputStreamReader(System.in));
    }

    /**
     * Read a single line from System.in
     * @return
     */
    public String readInputLine () {
        return readLine(new InputStreamReader(System.in));
    }

    private ArrayList<String> read (InputStreamReader in) {
        contents = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(in)) {
            String line;
            while ((line = br.readLine()) != null && !line.equals("")) {
                contents.add(line);
            }
        } catch (IOException e) {
            System.out.println("Could not read input \n" + e);
        }
        return contents;
    }

    private String readLine (InputStreamReader in) {
        line = "";
        try (BufferedReader br = new BufferedReader(in)) {
            line = br.readLine();
        } catch (IOException e) {
            System.out.println("Could not read input \n" + e);
        }
        return line;
    }

    public ArrayList<String> getLastRead() {
        return contents;
    }

    public String getLastReadLine() {
        return line;
    }
}
