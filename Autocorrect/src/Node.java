import java.util.ArrayList;
import java.util.List;

public class Node {
    boolean isWord;
    char content;
    List<Node> children;
    Node parent;

    public Node (boolean isWord, char content, Node parent) {
        this.content = content;
        this.isWord = isWord;
        this.parent = parent;
        children = new ArrayList<>();
    }
}
