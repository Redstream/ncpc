import java.util.HashMap;
import java.util.Scanner;

public class AutoCorrect {

    private long dictionaryWords;
    private long wordsToType;
    private Scanner sc;
    private TreeNode dictionary;

    public static void main(String[] args) {
        new AutoCorrect().compute();
    }

    private void compute() {
        sc = new Scanner(System.in);
        dictionaryWords = sc.nextLong();
        wordsToType = sc.nextLong();
        createDictionary();

        for (int i = 0; i < wordsToType; i++) {
            System.out.println(shortestPath(sc.nextLine()));
        }
    }

    private void createDictionary() {
        dictionary = new TreeNode(null, Character.MIN_VALUE);
        for(int i = 0; i < dictionaryWords; i++) {
            String word = sc.nextLine();
            TreeNode currentNode = dictionary;
            for(Character c : word.toCharArray()) {
                currentNode = new TreeNode(currentNode, c);
            }
        }
    }

    // TODO: 2017-10-02
    private int shortestPath(String word) {
        TreeNode endNode = getEndNode(word);
        TreeNode startNode = dictionary;
        return 0;
    }

    private TreeNode getEndNode(String word) {
        TreeNode endNode = dictionary;
        for (Character c : word.toCharArray()) {
            if (endNode.children.get(c) == null) {
                return endNode;
            }
            endNode = endNode.children.get(c);
        }
        return endNode;
    }

    class TreeNode {
        public TreeNode parent;
        public Character c;
        public HashMap<Character, TreeNode> children = new HashMap<>();
        public TreeNode prioChildren;
        public String preString;

        TreeNode(TreeNode parent, Character c){
            this.parent = parent;
            this.c = c;
            if(parent != null) {
                preString = parent.preString + c.toString();
                parent.addChildren(this);
            } else {
                preString = c.toString();
            }
        }

        public void addChildren(TreeNode child) {
            if(prioChildren == null) {
                prioChildren = child;
            }
            children.put(c, child);
        }

        public String getTabWord() {
             if(prioChildren == null) {
                 return preString;
             } else {
                 return prioChildren.getTabWord();
             }
        }

    }

}
