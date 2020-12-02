/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	/*
	@Test
	public void testGetNode()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.getNode(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first node", "A", shortList.getNode(0).data);
		assertEquals("Check second node", "B", shortList.getNode(1).data);
		
		try {
			shortList.getNode(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.getNode(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.getNode(i).data);
		}
		
		// test off the end of the longer array
		try {
			longerList.getNode(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.getNode(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	*/
	
	/** Test removing an element from the list.
	 * We've included the example from the concept challenge.
	 * You will want to add more tests.  */
	@Test
	public void testRemove()
	{
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
		//Check out of bounds on the index
		try {
			shortList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.remove(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the end of the longer array
		try {
			longerList.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.remove(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		//test removing from beginning of list
		shortList.add(2, "C");
		shortList.add(3, "D");
		shortList.add(4, "E");
		
		shortList.remove(0);
		//Should be B C D E
		assertEquals("Check correct size", 4, shortList.size);
		assertEquals("Check correct prev-curr connect", "B", shortList.head.next.data);
		//assertEquals("Check correct curr-prev connect", null, shortList.getNode(0).prev.data);
		//assertEquals("Check correct curr-next connect", "C", shortList.getNode(0).next.data);
		//assertEquals("Check correct next-curr", "B", shortList.getNode(1).prev.data);
		//assertEquals("Check correct val", "B", shortList.getNode(0).data);
		
		//test removing from middle of list
		shortList.remove(1);
		//Should be B D E
		//assertEquals("Check correct size", 3, shortList.size);
		//assertEquals("Check correct prev-curr connect", "D", shortList.getNode(0).next.data);
		//assertEquals("Check correct curr-prev connect", "B", shortList.getNode(1).prev.data);
		//assertEquals("Check correct curr-next connect", "E", shortList.getNode(1).next.data);
		//assertEquals("Check correct next-curr", "D", shortList.getNode(2).prev.data);
		//assertEquals("Check correct val", "D", shortList.getNode(1).data);
		
		//test removing from end of list
		shortList.remove(2);
		//Should be B D
		assertEquals("Check correct size", 2, shortList.size);
		//assertEquals("Check correct prev-curr connect", "D", shortList.getNode(0).next.data);
		//assertEquals("Check correct curr-prev connect", "B", shortList.getNode(1).prev.data);
		//assertEquals("Check correct curr-next connect", null, shortList.getNode(1).next.data);
		//assertEquals("Check correct next-curr", "D", shortList.tail.prev.data);
		//assertEquals("Check correct val", "D", shortList.getNode(1).data);
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		//test null add
		try {
			shortList.add(null);
			fail("Check null add");
		}
		catch (NullPointerException e) {
			
		}
		//test adding to empty list
		boolean test = emptyList.add(1);
		assertEquals("Check for success", true, test);
		assertEquals("Check correct head", "1", emptyList.head.next.toString());
		assertEquals("Check correct tail", "1", emptyList.tail.prev.toString());
		assertEquals("Check correct val", "1", emptyList.get(0).toString());
		assertEquals("Check correct size", 1, emptyList.size);
		
		
		//test adding to end of list
		shortList.add("C");
		assertEquals("Check correct size", 3, shortList.size);
		//assertEquals("Check correct prev-curr connect", "C", shortList.getNode(1).next.data);
		//assertEquals("Check correct curr-prev connect", "B", shortList.getNode(2).prev.data);
		//assertEquals("Check correct curr-next connect", null, shortList.getNode(2).next.data);
		assertEquals("Check correct tail", "C", shortList.tail.prev.toString());
		assertEquals("Check correct val", "C", shortList.get(2).toString());
		
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		//Test an empty list
		assertEquals("Test empty list", 0, emptyList.size);
		//Test a larger list
		assertEquals("Test list with values", 2, shortList.size);
		shortList.add("C");
		assertEquals("Test list with values added", 3, shortList.size);
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		
		//Check out of bounds on the index
		try {
			shortList.add(-1, "1");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.add(3, "1");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the end of the longer array
		try {
			longerList.add(-1,1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.add(LONG_LIST_LENGTH+1,1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		
		//test null add
		try {
			shortList.add(1, null);
			fail("Check null add");
		}
		catch (NullPointerException e) {
			
		}
		//test adding to empty list
		emptyList.add(0, 1);
		assertEquals("Check correct head", "1", emptyList.head.next.toString());
		assertEquals("Check correct tail", "1", emptyList.tail.prev.toString());
		assertEquals("Check correct val", "1", emptyList.get(0).toString());
		assertEquals("Check correct size", 1, emptyList.size);
		
		
		//test adding to beginning of list
		shortList.add(0, "C");
		assertEquals("Check correct size", 3, shortList.size);
		assertEquals("Check correct prev-curr connect", "C", shortList.head.next.data);
		//assertEquals("Check correct curr-prev connect", null, shortList.getNode(0).prev.data);
		//assertEquals("Check correct curr-next connect", "A", shortList.getNode(0).next.data);
		//assertEquals("Check correct next-curr", "C", shortList.getNode(1).prev.data);
		//assertEquals("Check correct val", "C", shortList.getNode(0).data);
		
		//test adding to middle of list
		shortList.add(1, "D");
		assertEquals("Check correct size", 4, shortList.size);
		//assertEquals("Check correct prev-curr connect", "D", shortList.getNode(0).next.data);
		//assertEquals("Check correct curr-prev connect", "C", shortList.getNode(1).prev.data);
		//assertEquals("Check correct curr-next connect", "A", shortList.getNode(1).next.data);
		//assertEquals("Check correct next-curr", "D", shortList.getNode(2).prev.data);
		//assertEquals("Check correct val", "D", shortList.getNode(1).data);
		
		//test adding to end of list
		shortList.add(4, "E");
		assertEquals("Check correct size", 5, shortList.size);
		//assertEquals("Check correct prev-curr connect", "E", shortList.getNode(3).next.data);
		//assertEquals("Check correct curr-prev connect", "B", shortList.getNode(4).prev.data);
		//assertEquals("Check correct curr-next connect", null, shortList.getNode(4).next.data);
		assertEquals("Check correct next-curr", "E", shortList.tail.prev.data);
		//assertEquals("Check correct val", "E", shortList.getNode(4).data);
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		//Check out of bounds on the index
		try {
			shortList.set(-1, "1");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.set(2, "1");
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		
		// test off the end of the longer array
		try {
			longerList.set(-1,1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.set(LONG_LIST_LENGTH,1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		
		//test null add
		try {
			shortList.set(1, null);
			fail("Check null add");
		}
		catch (NullPointerException e) {
			
		}
		
		//Check return
		String testVal = shortList.set(1, "C");
		assertEquals("Check correct val", "B", testVal);
		//Check replacement
		assertEquals("Check correct val", "C", shortList.get(1));
		
	}
	
	
	// TODO: Optionally add more test methods.
	
}
