import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class AutoCorrect {

    private  long dictionaryWords;
    private  long wordsToType;
    private  Scanner sc;
    private  TreeNode dictionary;

    public static void main(String[] args) {
        new AutoCorrect().compute();
    }

    public void compute() {
        sc = new Scanner(System.in);
        dictionaryWords = sc.nextLong();
        wordsToType = sc.nextLong();
        createDictionary();
    }

    public void createDictionary() {
        dictionary = new TreeNode(null, Character.MIN_VALUE);
        for(int i = 0; i < dictionaryWords; i++) {
            String word = sc.nextLine();
            TreeNode currentNode = dictionary;
            for(Character c : word.toCharArray()) {
                TreeNode newNode = new TreeNode(currentNode, c);
                currentNode = newNode;
            }
        }
    }

    class TreeNode {
        private TreeNode parent;
        private Character c;
        private HashMap<Character, TreeNode> children = new HashMap<>();
        private TreeNode prioChildren;
        private String preString;

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

        public TreeNode getChildrenAtCharacter(Character c) {
            return children.get(c);
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
