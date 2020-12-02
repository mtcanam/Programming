package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

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
		// TODO: Implement this method
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return -1;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// TODO: Implement this method
		return null;
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
		// TODO: Implement this method
		return null;
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
	LLNode<E> prev;
	LLNode<E> next;
	E data;

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

}
