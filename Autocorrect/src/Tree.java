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
            while (parNode != null) {                   //Loop until root
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
        int strokes = 0;                    //Initialize to 1 in order to assume that first character of word has been written

        char[] chars = word.toCharArray();
        FindResult res = this.findBestMatch(chars);
        strokes = res.cost;                 //Get cost to reach index, add cost of potentially erasing
        int index = res.index;                          //Get the character we've reached
        while(index < chars.length) {                   //Add remaining keystrokes if any
            strokes++;
            index++;
        }

        if(strokes < minStrokes) {
            minStrokes = strokes;
        }

        return minStrokes;
    }

    public FindResult findBestMatch2 (char[] chars) {
        Node current = root;
        int index = 1;
        int costThisFar = 1;
        boolean tabPossibleIn = false;
        boolean tabPossible = false;
        int tabsTraversed = 0;

        while(true) {
            Node next = null;                                                                   //Ref to next node
            for(int i = 0; ((i < current.children.size()) && (index < chars.length)); i++) {    //Iterate through children if there are more characters
                Node child = current.children.get(i);                   //Get next child
                if(child != null && child.content == chars[index]) {    //If child isn't null and contents match
                    next = child;                                       //Point next to it
                    if(i == 0) {                                        //If leftmost child
                        tabPossible = true;                             //Tabbing there is possible
                    }
                    break;                                              //Once next is found we break
                }
            }

            if(next == null) {                                          //If next is null we didn't find a good child or we're out of characters, i.e we're done
                if(tabPossibleIn) {     //If we could tab here
                    int tabCost = calculateTabCost(current);          //Calculate cost of tabbing here
                    if(tabCost < tabsTraversed) {
                        costThisFar += tabCost;
                    }
                    else {
                        costThisFar += tabsTraversed;
                    }
                }
                else {                  //If we couldn't tab here
                    return new FindResult(costThisFar, index);   //Not tabbing here means no extra cost
                }
            }
        }
    }

    //Find the string in the tree that matches the input the best
    public FindResult findBestMatch (char[] chars) {
        FindResult res = findBestMatchHelp(root, chars, 1, 1, false, 0);
        return res;
    }

    public FindResult findBestMatchHelp (Node current, char[] chars, int index, int costThisFar, boolean tabPossibleIn, int tabsTraversed) {
        Node next = null;
        boolean tabPossible = false;
        for(int i = 0; (i < current.children.size()) && (index < chars.length); i++) {  //Find the next node to go to if there are letters left
            Node child = current.children.get(i);           //Get current child
            if(child != null) {                             //If the child isn't null
                if(child.content == chars[index]) {         //If the contents match, go here next
                    next = child;
                    if(i == 0) {                            //If this was the leftmost child it's reachable by a tab
                        tabPossible = true;
                    }
                    break;
                }
            }
        }

        FindResult result = null;

        if(next == null) {                      //If next is null we got no where to go, return
            if(tabPossibleIn) {                 //If we could tab here
                int tabCost = calculateTabCost(current);            //Calculate cost of tabbing here
                if(tabCost < tabsTraversed) {                       //Check if it was cheaper to tab to the end
                    costThisFar += tabCost;
                }
                else {                                              //If it wasn't, take regular cost
                    costThisFar += tabsTraversed;
                }
                return new FindResult(costThisFar,index);
            }
            else {
                //costThisFar++;                  //If we didn't tab here, just increase cost and return
                return new FindResult(costThisFar,index);
            }
        }
        if(next != null) {          //If next is not null we know where to go next.
            index++;                //Increment index before next recursive call
            if(tabPossibleIn && tabPossible) {   //If it was possible to tab to this node and we can continue to tab
                tabsTraversed ++;                //Increment tabsTraversed when moving to next
                result = findBestMatchHelp(next, chars, index, costThisFar, true, tabsTraversed);
            }
            else if(!tabPossibleIn && tabPossible) {    //If we couldn't tab to the current node but can tab to next,
                tabsTraversed = 0;                      //Initialize tabs traversed
                costThisFar++;                          //Increment since either we will press tab or press the key to move to next
                result = findBestMatchHelp(next, chars, index, costThisFar, true, tabsTraversed);
            }
            else if(tabPossibleIn && !tabPossible){         //If we could tab here but we can't continue tabbing
                int costOfTab = calculateTabCost(current);  //Calculate cost of tabbing here, tab + possible backspaces
                if(costOfTab < tabsTraversed) {             //If it was cheaper to tab here, add that cost to costThisFar, otherwise take cost of keystrokes
                    costThisFar += costOfTab;
                }
                else {
                    costThisFar += tabsTraversed;
                }
                costThisFar++;                              //Add cost of moving to next
                result = findBestMatchHelp(next, chars, index, costThisFar, false, 0 ); //?????
            }
            else { // !tabPossibleIn !tabPossible - not possible to tab
                costThisFar++;      //Add cost of moving to next
                result = findBestMatchHelp(next, chars, index, costThisFar, false, 0);
            }
        }
        return result;
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
        int cost;
        int index;

        public FindResult(int cost, int index) {
            this.cost = cost;
            this.index = index;
        }

    }
}
