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
                currentNode.children.add(null);     //Note that this node marks the end of a word
                break;                              //Done!
            }
            else {                                      //Not the last character
                Node nextNode = null;                   //Reference to next node
                char nextChar = chars[i+1];             //Get next char
                for(Node child : currentNode.children) {
                    if(child == null) {
                        continue;
                    }
                    else if(child.content == nextChar) {     //If this node holds the next char
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
        if (n.isWord()) {
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
            if(child != null) {
                printHelp(child);
            }
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
        findBestMatchHelp(root, chars, 0, 1, false, 0);
        return null;
    }

    public void findBestMatchHelp (Node current, char[] chars, int index, int costThisFar, boolean tabPossibleIn, int tabsTraversed) {
        Node next = null;
        boolean tabPossible = false;
        for(int i = 0; i < current.children.size(); i++) {  //Find the next node to go to
            Node child = current.children.get(i);           //Get current child
            if(child != null) {                             //If the child isn't null
                if(child.content == chars[index]) {         //If the contents match, go here next
                    next = child;
                    if(i == 0) {                            //If this was the leftmost child it's reachable by a tab
                        tabPossible = true;
                    }
                }
            }
        }
        if(next == null) {          //If next is null we got no where to go, return
            //calculate cost to reach next
            int cost = costThisFar;
        }
        if(next != null) {          //If next is not null we know where to go next.
            index++;                //Increment index before next recursive call
            if(tabPossibleIn && tabPossible) {   //If it was possible to tab to this node and we can continue to tab
                tabsTraversed ++;                //Keep track of nodes we've traversed by tabbing
                findBestMatchHelp(next, chars, index, costThisFar, true, tabsTraversed);
            }
            else if(!tabPossibleIn && tabPossible) {    //If we couldn't tab to the current node but can tab to next
                tabsTraversed++;
                findBestMatchHelp(next, chars, index, costThisFar, true, tabsTraversed);
            }
            else if(tabPossibleIn && !tabPossible){     //If we could tab here but we can't continue tabbing
                int costOfTab = 1 + calculateTabCost(current); //Check if it was beneficial to tab here, otherwise take cost of keystrokes
                costThisFar = costThisFar + ((costOfTab < tabsTraversed)? costOfTab:tabsTraversed);     //If it was cheaper to tab here, add that cost to costThisFar, otherwise take cost of keystrokes
                findBestMatchHelp(next, chars, index, costThisFar, false, 0 ); //?????
            }

        }

    }

    //Given a node, which can be reached by tabbing, calculates the cost of reaching it (if anything needs to be erased beyond it)
    public int calculateTabCost(Node node) {
        int cost = 0;
        while(true) {
            if(node.children.get(0) == null) {  //Since this node is reachable by a tab, at some point it's leftmost child must be null
                break;
            }
            else {
                node = node.children.get(0);
                cost++;                         //For each node we traverse the cost increases
            }
        }
        return cost;
    }

    class FindResult {
        int tabs;

    }
}
