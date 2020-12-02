package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	public LLNode<E> head;
	public LLNode<E> tail;
	public int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null, null, tail);
		tail = new LLNode<E>(null, head, null);
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		//First checks: make sure that the element is not null.
		if(element == null) {
			throw new NullPointerException();
		}	
		//If not null, then add to end of list.
		//Need to change the tail, the previously last item, and the size
		//First check whether list is size zero, so that we're essentially adding it as an entry
		if(size == 0) {
			//First create node
			LLNode<E> currNode = new LLNode<E>(element, head, tail);
			//Adjust the tail and head
			head.next = currNode;
			tail.prev = currNode;
		}else {
			//Otherwise, find the last node
			LLNode<E> lastNode = tail.prev;
			LLNode<E> currNode = new LLNode<E>(element, lastNode, tail);
			lastNode.next = currNode;
			tail.prev = currNode;
		}
		size++;
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index)
	{
		//Throw error if the index is outside of bounds
		if(index > this.size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}	
		//Get the 0th node from the sentinel
		LLNode<E> currNode = head.next;
		//Iterate through the elements from head onwards to find the index element. Could step through from tail if index is closer to size than 0 as well.
		for (int i = 0; i < index; i++) {
			currNode = currNode.next;
		}
		return currNode.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		//Set the size to an int for readability
		int listLength = this.size;
		//First checks: make sure that the element is not null.
		if(element == null) {
			throw new NullPointerException();
		}	
		//If not null, then check that index exists.
		if(index > listLength || index < 0) {
			throw new IndexOutOfBoundsException();
		}	
		//Need to change the connections of the index-1 next param, and the index prev param
		//Also need to add a node with value E, prev as teh index-1 node, and next as index node
		//Three edge cases are adding the size index (ie to the end of the list) or adding the 0th index (ie adding the first element), or adding to an empty list
		if(listLength == 0) {
			//This list was formerly empty
			LLNode<E> currNode = new LLNode<E>(element, head, tail);
			head.next = currNode;
			tail.prev = currNode;
		}else if(index == 0) {
			//We are adding to the start of the list, but the list has an element already
			//Set the replacement node (ie the node to be shifted right)
			LLNode<E> repNode =  this.getNode(index);
			//Then create new node
			LLNode<E> currNode = new LLNode<E>(element, head, repNode);
			//Adjust the adjacent nodes connections
			head.next = currNode;
			repNode.prev = currNode;
		}else if(index == listLength){
			//We are adding to the end of the list that already has an element
			//Set the previous node (ie the node to be connected to new node)
			LLNode<E> prevNode =  this.getNode(index - 1);
			//Then create new node
			LLNode<E> currNode = new LLNode<E>(element, prevNode, tail);
			//Adjust the adjacent nodes connections
			prevNode.next = currNode;
			tail.prev = currNode;
		}else {
			//Otherwise, we are adding to the middle of the list somewhere
			//Set the previous node (ie the node to be connected to new node)
			LLNode<E> prevNode =  this.getNode(index - 1);
			//Set the replacement node (ie the node to be shifted right)
			LLNode<E> repNode =  this.getNode(index);
			//Then create new node
			LLNode<E> currNode = new LLNode<E>(element, prevNode, repNode);
			//Adjust the adjacent nodes connections
			prevNode.next = currNode;
			repNode.prev = currNode;
		}
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		//Simply return the size of the object
		return this.size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		//Throw error if the index is outside of bounds
		if(index > this.size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}	
		
		//Set the size to an int for readability
		int listLength = this.size;
		
		//Get the node value to return
		E nodeVal = get(index);

		//Edge cases: removing the first node, last node, or removing the only node
		if(listLength == 1) {
			//This list has only one element
			head.next = tail;
			tail.prev = head;
		}else if(index == 0) {
			//We are remving the first node
			//Set the next node (ie the node to be shifted left)
			LLNode<E> nextNode =  this.getNode(1);
			//Adjust the adjacent nodes connections
			head.next = nextNode;
			nextNode.prev = head;
		}else if(index == listLength - 1){
			//We are reomving the last node
			//Set the previous node (ie the node to be connected to new node)
			LLNode<E> prevNode =  this.getNode(index - 1);
			//Adjust the adjacent nodes connections
			prevNode.next = tail;
			tail.prev = prevNode;
		}else {
			//Otherwise, we are removing from the middle of the list somewhere
			//Set the previous node 
			LLNode<E> prevNode =  this.getNode(index - 1);
			//Set the next node 
			LLNode<E> nextNode =  this.getNode(index+1);
			//Adjust the adjacent nodes connections
			prevNode.next = nextNode;
			nextNode.prev = prevNode;
		}
		
		//Decrement the size
		this.size--;
		
		return nodeVal;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		//Throw error if the index is outside of bounds
		if(index > this.size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}
		//First checks: make sure that the element is not null.
		if(element == null) {
			throw new NullPointerException();
		}
		
		//Get the node value to return
		E nodeVal = get(index);
		
		//Get the node to replace
		LLNode<E> currNode = getNode(index);
		//Set val
		currNode.data = element;
		
		return nodeVal;
	}   
	
	public LLNode<E> getNode(int index){
		//Throw error if the index is outside of bounds
		if(index > this.size-1 || index < 0) {
			throw new IndexOutOfBoundsException();
		}	
		//Get the 0th node from the sentinel
		LLNode<E> currNode = head.next;
		//Iterate through the elements from head onwards to find the index element. Could step through from tail if index is closer to size than 0 as well.
		for (int i = 0; i < index; i++) {
			currNode = currNode.next;
		}
		return currNode;
	}
	
}

class LLNode<E> 
{
	public LLNode<E> prev;
	public LLNode<E> next;
	public E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public LLNode(E e, LLNode<E> prevNode, LLNode<E> nextNode) {
		this.data = e;
		this.prev = prevNode;
		this.next = nextNode;
	}
	
	public String toString() {
		return data.toString();
	}
	
	public E getData() {
		return this.data;
	}

}
