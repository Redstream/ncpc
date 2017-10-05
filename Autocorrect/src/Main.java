import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MyReader r = new MyReader(Paths.get("in.txt"));
        r.read();

        List<String> wordsInDictionary = r.wordsInDictionary;
        HashMap<Character, Tree> roots = new HashMap<>();

        for(String s : wordsInDictionary) {
            char firstChar = s.charAt(0);       //Get first char for this words
            Tree charTree = null;
            if(roots.containsKey(firstChar)) {      //There is a tree with a root that begins on this character
                charTree = roots.get(firstChar);    //Fetch tree
            }
            else {
                charTree = new Tree(new Node(false, firstChar, null));  //Create a tree if there isn't one
                roots.put(firstChar, charTree);                         //Add to map
            }

            charTree.add(s);                    //Add word to tree
        }

        /*for(Tree t :roots.values()){
            t.printWordsInTree();
        }*/


        List<String> wordsToType = r.wordsToType;

        //Test of calculation of tabs
        /*String austria = "autocorrect";
        char[] chars = austria.toCharArray();
        Tree tree = roots.get(chars[0]);
        Node node = tree.root;
        int index = 1;
        while(true) {
            Node next = null;
            for(int i = 0; i < node.children.size(); i++) {
                Node child = node.children.get(i);
                if(child != null) {
                    if(child.content == chars[index] && i == 0) {
                        next = child;
                    }
                }
            }
            if(next == null) { //We're at the end
                System.out.println(tree.calculateTabCost(node)+1);
                break;
            }
            else {
                node = next;
                index++;
            }
        }*/

        for(String word : wordsToType) {
            System.out.println("\n" + word);
            char c = word.charAt(0);
            Tree t = roots.get(c);
            if(t == null) {
                System.out.println("Tree doesnt exist");
                System.out.println(word.length());
            }
            else {
                System.out.println(t.calculate(word));
            }
        }
    }
}
