package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;
    private int size;
    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    String lWord = word.toLowerCase();
	    int wordLen = word.length();
	    boolean newNodes = false;
	    boolean newEnd = false;
	    boolean newWord = false;
	    
	    //Make sure that the word is not nothing
	    if(wordLen == 0) {
	    	throw new NullPointerException();
	    }else if(wordLen == 1) {
	    	char c = lWord.charAt(0);
	    	TrieNode currNode = root;
	    	//Try to insert the character (which is the full string) as a root child.
		    if(currNode.getValidNextCharacters().contains(c)) {
	    		//This character is already a subnode of the parent, just get it
	    		currNode = currNode.getChild(c);
	    	}else {
	    		//This character is new, we need to add it
	    		//Since there's a new character, we need to mark that there were new nodes added
	    		currNode = currNode.insert(c);
	    		newNodes = true;
	    	}
    		newEnd = !currNode.endsWord();
    		if(newNodes || newEnd) {
    			newWord = true;
    			currNode.setEndsWord(true);
    		}
	    }
	    //Try to insert the character as a root child.
	    TrieNode currNode = root.insert(lWord.charAt(0));
	    //if this is null, we can just get the node from 
	    if (currNode == null) {
	    	currNode = root.getChild(lWord.charAt(0));
	    }
	    for (int i = 1; i < wordLen; i++) {
	    	char c = lWord.charAt(i);
	    	//For each character, try to add it as a child node of the previous character
	    	//If we get null, we need to get instead, since that node already existed
	    	if(currNode.getValidNextCharacters().contains(c)) {
	    		//This character is already a subnode of the parent, just get it
	    		currNode = currNode.getChild(c);
	    	}else {
	    		//This character is new, we need to add it
	    		//Since there's a new character, we need to mark that there were new nodes added
	    		currNode = currNode.insert(c);
	    		newNodes = true;
	    	}
	    	//At this point, we know whether any new nodes were added. The two conditions on a new word are either new nodes added, or a new word end (ie a shorter version of a previous string)
	    	if(i == wordLen - 1) {
	    		//Determine whether this is a new ending
	    		newEnd = !currNode.endsWord();
	    		if(newNodes || newEnd) {
	    			newWord = true;
	    			currNode.setEndsWord(true);
	    		}
	    	}
	    }
	    
	    if(newWord) {
	    	size++;
	    }
	    return newWord;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    return size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
		String lS = s.toLowerCase();
		int sLen = s.length();
		//Make sure that the word is not nothing
	    if(sLen == 0) {
	    	return false;
	    }
		TrieNode currNode = root.getChild(lS.charAt(0));
		//if we fail on the first node, just return false
		if (currNode == null) {
			return false;
	    }
		//Otherwise iterate through characters and check whether the nodes exist
	    for (int i = 1; i < sLen; i++) {
	    	currNode = currNode.getChild(lS.charAt(i));
	    	if (currNode == null) {
	    		//Next child not found, string doens't exist
	    		return false;
	    	}
	    	if (i == sLen - 1 && currNode.endsWord()) {
	    		//if we're at the last character, and no nulls, ad this ends a word, return true
	    		return true;
	    	}
	    }
		return false;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 
    	 //Initialize results
    	 List<String> arr = new ArrayList<String>();
    	 int wordCnt = 0;
    	 TrieNode currNode = root;
    	 
    	 //Find the stem
    	 String lS = prefix.toLowerCase();
 		int sLen = prefix.length();
 		//Make sure that the word is not nothing
 	    if(sLen == 0) {
    		Queue<TrieNode> q = new LinkedList<TrieNode>();
    		q.add(currNode);
    		while(wordCnt < numCompletions && !q.isEmpty()) {
    			currNode = q.remove();
    			if(currNode.endsWord()) {
    				arr.add(currNode.getText());
    				wordCnt++;
    			}
				for(char c:currNode.getValidNextCharacters()) {
					q.add(currNode.getChild(c));
    			}
    		}
 	    }
 		//Otherwise iterate through characters and check whether the nodes exist
 	    for (int i = 0; i < sLen; i++) {
    		currNode = currNode.getChild(lS.charAt(i));
 	    	if (currNode == null) {
 	    		//Next child not found, string doens't exist
 	    		return arr;
 	    	}
 	    	if (i == sLen - 1) {
 	    		//if we're at the last character, and no nulls, attempt the autocomplete
 	    		Queue<TrieNode> q = new LinkedList<TrieNode>();
 	    		q.add(currNode);
 	    		while(wordCnt < numCompletions && !q.isEmpty()) {
 	    			currNode = q.remove();
 	    			if(currNode.endsWord()) {
 	    				arr.add(currNode.getText());
 	    				wordCnt++;
 	    			}
    				for(char c:currNode.getValidNextCharacters()) {
 	    					q.add(currNode.getChild(c));
 	    			}
 	    			
 	    		}
 	    	}
 	    }
 	    
        return arr;
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}