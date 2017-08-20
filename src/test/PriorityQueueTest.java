package test;



import org.junit.Assert;
import org.junit.Test;

import priorityqueue.PriorityItem;
import priorityqueue.ThreadSafePriorityQueue;

public class PriorityQueueTest {
	
    @Test
	public void enqueueTest() throws Exception
	{
    	ThreadSafePriorityQueue testQueue= new ThreadSafePriorityQueue(5, 2);
    	testQueue.enqueue("A", 1);
    	Assert.assertEquals(testQueue.isEmpty(), false);
	}
    
    @Test
	public void enqueueSizeTest() throws Exception
	{
    	ThreadSafePriorityQueue testQueue= new ThreadSafePriorityQueue(5, 2);
    	testQueue.enqueue("A", 1);
    	Assert.assertEquals(testQueue.size(), 1);
	}
    
    @Test(expected = Exception.class)
    public void checkEnqueueSizeException() throws Exception
    {
    	ThreadSafePriorityQueue testQueue= new ThreadSafePriorityQueue(1, 2);
    	testQueue.enqueue("A", 1);
    	testQueue.enqueue("A", 1);
    }
    
    @Test(expected = Exception.class)
	public void dequeueEmptyTest() throws Exception
	{
    	ThreadSafePriorityQueue testQueue= new ThreadSafePriorityQueue(5, 2);
			testQueue.dequeue();
	}
    
    @Test
    public void dequeueTest001() throws Exception
    {
      ThreadSafePriorityQueue threadSafePriorityQueue = new ThreadSafePriorityQueue(35, 2);
		
		threadSafePriorityQueue.enqueue("a", 4);
		threadSafePriorityQueue.enqueue("b", 1);
		threadSafePriorityQueue.enqueue("c", 3);
		threadSafePriorityQueue.enqueue("d", 2);
		
		threadSafePriorityQueue.enqueue("e", 1);
		threadSafePriorityQueue.enqueue("f", 4);
		threadSafePriorityQueue.enqueue("g", 2);
		threadSafePriorityQueue.enqueue("h", 3);
		
		threadSafePriorityQueue.enqueue("i", 2);
		threadSafePriorityQueue.enqueue("j", 4);
		threadSafePriorityQueue.enqueue("k", 1);
		threadSafePriorityQueue.enqueue("l", 3);
		
		threadSafePriorityQueue.enqueue("m", 3);
		threadSafePriorityQueue.enqueue("n", 5);
		threadSafePriorityQueue.enqueue("o", 2);
		threadSafePriorityQueue.enqueue("p", 1);
		
		threadSafePriorityQueue.enqueue("q", 3);
		threadSafePriorityQueue.enqueue("r", 6);
		threadSafePriorityQueue.enqueue("s", 1);
		threadSafePriorityQueue.enqueue("t", 2);
		
		threadSafePriorityQueue.enqueue("u", 4);
		threadSafePriorityQueue.enqueue("v", 2);
		threadSafePriorityQueue.enqueue("w", 4);
		threadSafePriorityQueue.enqueue("x", 1);
		
		threadSafePriorityQueue.enqueue("y", 3);
		threadSafePriorityQueue.enqueue("z", 2);
		threadSafePriorityQueue.enqueue("aa", 1);
		threadSafePriorityQueue.enqueue("bb", 5);
		
		threadSafePriorityQueue.enqueue("cc", 2);
		threadSafePriorityQueue.enqueue("dd", 1);
		threadSafePriorityQueue.enqueue("ee", 1);
		threadSafePriorityQueue.enqueue("ff", 2);
		
		threadSafePriorityQueue.enqueue("gg", 3);
		threadSafePriorityQueue.enqueue("hh", 1);
		threadSafePriorityQueue.enqueue("ii", 1);
		
		String correctOp = "11211231121123411212322345233434564";
		
		for(int i=0; i<35; i++)
		{
			int testPriority = threadSafePriorityQueue.dequeue().getPriority();
			int correctPriority = Character.getNumericValue(correctOp.charAt(i));
			Assert.assertEquals(testPriority, correctPriority);
			
		}
    	
    }
    
    @Test
    public void dequeueTest002() throws Exception
    {
      ThreadSafePriorityQueue threadSafePriorityQueue = new ThreadSafePriorityQueue(6, 2);
        threadSafePriorityQueue.enqueue("a", 4);
		threadSafePriorityQueue.enqueue("b", 1);
		threadSafePriorityQueue.enqueue("c", 3);
		threadSafePriorityQueue.enqueue("d", 2);
		threadSafePriorityQueue.enqueue("e", 1);
		threadSafePriorityQueue.enqueue("f", 2);
		
		int testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 1);
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 1);
		threadSafePriorityQueue.enqueue("g", 1);
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 2);
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 1);
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 2);
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 3);
		
    }
    
    @Test
    public void dequeueCheckInsertionOrderTest003() throws Exception
    { 
        ThreadSafePriorityQueue threadSafePriorityQueue = new ThreadSafePriorityQueue(6, 2);
        threadSafePriorityQueue.enqueue("a", 4);   
		threadSafePriorityQueue.enqueue("b", 1);  // has timestamp 1
		threadSafePriorityQueue.enqueue("c", 3);
		threadSafePriorityQueue.enqueue("d", 2); // has timestamp 3
		threadSafePriorityQueue.enqueue("e", 2); // has timestamp 4
		 
		PriorityItem testItem =  threadSafePriorityQueue.dequeue();
		int testPriority = testItem.getPriority();
		int timeStamp = testItem.getTimeStamp();
		Assert.assertEquals(testPriority, 1);
		Assert.assertEquals(timeStamp, 1);
		
		testItem =  threadSafePriorityQueue.dequeue();
		testPriority = testItem.getPriority();
		timeStamp = testItem.getTimeStamp();
		Assert.assertEquals(testPriority, 2);
		Assert.assertEquals(timeStamp, 3);
		
		threadSafePriorityQueue.enqueue("g", 1);  // has timestamp 5
		
		testItem =  threadSafePriorityQueue.dequeue();
		testPriority = testItem.getPriority();
		timeStamp = testItem.getTimeStamp();
		Assert.assertEquals(testPriority, 1);
		Assert.assertEquals(timeStamp, 5);
		
		testItem =  threadSafePriorityQueue.dequeue();
		testPriority = testItem.getPriority();
		timeStamp = testItem.getTimeStamp();
		Assert.assertEquals(testPriority, 2);
		Assert.assertEquals(timeStamp, 4);
		
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 3);
		testPriority = threadSafePriorityQueue.dequeue().getPriority();
		Assert.assertEquals(testPriority, 4);
		
    }
}
