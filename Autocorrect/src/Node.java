import java.util.ArrayList;
import java.util.List;

public class Node {
    char content;
    List<Node> children;
    Node parent;

    public Node (boolean isWord, char content, Node parent) {
        this.content = content;
        this.parent = parent;
        children = new ArrayList<>();
        if(isWord) {
            children.add(null);
        }
    }

    public boolean isWord() {
        if(this.children.contains(null)) {
            return true;
        }
        return false;
    }
}
