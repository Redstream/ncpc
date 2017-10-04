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

        for(String word : wordsToType) {
            roots.get(word.charAt(0)).calculate(word);
        }
    }
}
