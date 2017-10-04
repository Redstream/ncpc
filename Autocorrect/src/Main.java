import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        MyReader r = new MyReader(Paths.get("in.txt"));
        r.read();
    }
}
