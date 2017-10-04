import java.util.LinkedList;
import java.util.List;

public class Tree {
    Node root;

    public Tree (Node root) {
        this.root = root;
    }

    //Adds a word to the tree
    public void add(String word) {
        char[] chars = word.toCharArray();          //Convert string to char array
        Node currentNode = root;                    //Start in the root
        for(int i = 0; i < chars.length; i++) {     //Iterate through all chars
            if(i == (chars.length - 1)) {           //If this is the last character of the word
                currentNode.isWord = true;          //Note that this node marks the end of a word
                break;                              //Done!
            }
            else {                                      //Not the last character
                Node nextNode = null;                   //Reference to next node
                char nextChar = chars[i+1];             //Get next char
                for(Node child : currentNode.children) {
                    if(child.content == nextChar) {     //If this node holds the next char
                        nextNode = child;               //Point next to it
                    }
                }
                if(nextNode == null) {                                   //If no such node exists, create one
                    nextNode = new Node(false, nextChar, currentNode);   //Create new node
                    currentNode.children.add(nextNode);                  //Add new node to children in parent
                }

                currentNode = nextNode;                     //Point current to next
            }

        }
    }

    public void printWordsInTree() {
        printHelp(root);
    }

    private void printHelp (Node n) {
        if (n.isWord) {
            Node parNode = n;                           //Set parent to node initially
            List<Character> chars = new LinkedList<>(); //Chars to print
            while (parNode != null) {             //Loop until root
                chars.add(parNode.content);             //Add character to list
                parNode = parNode.parent;               //Repoint parNode
            }
            for (int i = chars.size(); i > 0; i--) {
                System.out.println(chars.get(i - 1));
            }
            System.out.println("");
        }
        for (Node child : n.children) {
            printHelp(child);
        }
    }

    //Calculates how many keystrokes you have to do in order to type this word using this tree
    public int calculate (String word) {
        int minStrokes = word.length();     //Initialize minimal to length of word (just writing it out)
        int strokes = 1;                    //Initialize to 1 in order to assume that first character of word has been written

        char[] chars = word.toCharArray();
        FindResult res = this.findBestMatch(chars);

        return 0;
    }

    //Find the string in the tree that matches the input the best
    public FindResult findBestMatch (char[] chars) {
        Node current = root;
        FindResult result = new FindResult();
        findBestMatchHelp(current, chars, result, 0);
        return result;
    }

    public void findBestMatchHelp (Node current, char[] remaining, FindResult result, int index) {
        if(current.content == remaining[index]) {   //If the content of the current node matches the current character

            index++;                                //Look at the next char

            for(int i = 0; i < current.children.size(); i++) {
                Node nextChild = current.children.get(i);       //Get next child to process
                if(nextChild.content == remaining[index]) {     //If this child's contents match with the next char, go there

                    findBestMatchHelp();
                }
            }

            return;
        }
        else {      //No match, this is the best possible match, return
            return;
        }
    }

    class FindResult {
        int tabs;

    }
}
