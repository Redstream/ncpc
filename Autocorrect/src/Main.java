import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        String inFile = "./testdata/19-generated.in";
        String ansFile = "./testdata/19-generated.ans";
        MyReader r = new MyReader(Paths.get(inFile));
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
        List<Integer> myAnswers = new LinkedList<>();
        for(String word : wordsToType) {
            char c = word.charAt(0);
            Tree t = roots.get(c);
            if(t == null) {
                myAnswers.add(word.length());
            }
            else {
                int answer = t.calculate(word);
                myAnswers.add(answer);
            }
        }


        List<Integer> theirAnswers = new LinkedList<>();
        try {
            List<String> readAnswers = Files.readAllLines(Paths.get(ansFile));
            for(String ans : readAnswers) {
                theirAnswers.add(Integer.parseInt(ans));
            }

        }
        catch(IOException e) {
            e.printStackTrace();
        }

        int errors = 0;
        for(int i = 0; i < theirAnswers.size(); i++) {
            if(theirAnswers.get(i).intValue() != myAnswers.get(i).intValue()) {
                //System.out.println("Failed!");
                System.out.println("My answer:" + myAnswers.get(i) + ", their answer: " + theirAnswers.get(i));
                errors++;
            }
            else {
                //System.out.println("Success");
            }
        }
        System.out.println("Errors: " + errors);
    }
}
