package priorityqueue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author Vignesh Aravindan
 * ThreadSafePriorityQueue class - consists of enqueue and dequeue methods, that will be used by producer-consumer threads
*/
public class ThreadSafePriorityQueue {

	// Java priority queue that follows binary heap to store min heap item at
	// head of queue
	private Queue<PriorityItem> priorityQueue;
	// Map to store the priority of the dequeued items and corresponding count
	// <key:priority, value:occurences>
	private Map<Integer, Integer> recordOcc;
	// arbitary burst rate for the class
	private int burstRate;
	// an incremental counter to maintain fifo, when dequeuing items of same
	// priority
	private int timeStampCounter = 0;
    private int capacity;
	/**
	 * Constructor - 
	 * initializes the queue with user determined queue capacity and a fifoComparator that maintains fifo order
	 * initializes the hashmap that is used to keep track of the burst rate of of the priority
	 * PROBLEM B - Burst rate is made to be user defined and will be added as a constructor parameter in the main thread 'ThreadStart'
	 * @param capacity         
	 * @param burstRate
	 */
	public ThreadSafePriorityQueue(int capacity, int burstRate) {
		priorityQueue = new PriorityQueue<PriorityItem>(capacity, fifoComparator);
		recordOcc = new HashMap<Integer, Integer>();
		this.capacity = capacity;
		this.burstRate = burstRate;
	}

	/**
	 *	Function to enqueue
	 * @param value         
	 * @param Priority
	 * @throws Exception 
	 */
	public void enqueue(String value, int priority) throws Exception {
		if(size() >= capacity)
		{
			throw new Exception("Cannot insert into full queue");
		}
		PriorityItem item = new PriorityItem(value, priority);
		item.setTimeStamp(timeStampCounter++);
		priorityQueue.add(item);
	}

	/**
	 *	Function to dequeue
	 * @throws Exception 
	 */
	public PriorityItem dequeue() throws Exception {
		if(isEmpty())
		{
			throw new Exception("Cannot delete from empty queue");
		}
		// initial, when first item is removed
		if (recordOcc.isEmpty()) {
			// helper function to record the count of the priority of the item
			// being polled
			recordPriority(priorityQueue.peek());
			// poll highest priority item located in head of queue
			return priorityQueue.poll();
		} else {
			// get the firstkey (priority) in the recordOcc map (ordered in desc
			// order of it's values)
			int firstKey = recordOcc.keySet().iterator().next();
			// check if it's value (ie. no of occurences of the priority) is
			// equal to burst rate
			if (recordOcc.get(firstKey) == burstRate) {

				// if yes, then increment the priority by 1 to get the next
				// priority to be polled
				int nextBurstItem = firstKey + 1;
				// since the priority reached the burst rate, it can be cleared
				// from the map to start afresh
				recordOcc.remove(firstKey);

				// helper function to check in fifo order, if there exists an
				// item that has 'P+1' priority
				PriorityItem pr = containsPriority(nextBurstItem);

				// if null, then there is no P+1 priority item, which leads to
				// PROBLEM A1, simple way to overcome it is by polling
				// the current head of the queue, by this way we maintain
				// importance of polling higher priority items first
				if (pr == null) {
					recordPriority(priorityQueue.peek());
					return priorityQueue.poll();
				} else {
					// if found, the priority of the item is recorded in the map
					recordPriority(pr);
					return pr;
				}
			} // if not equal to burst rate, continue to poll highest priority
				// item located in head of queue
			else {
				recordPriority(priorityQueue.peek());
				return priorityQueue.poll();
			}
		}

	}

	public int size() {
		return priorityQueue.size();
	}

	public boolean isEmpty() {

		return priorityQueue.isEmpty();
	}
	
	
	/**
	 *	Function to store <key:priority, value:occurences> in map
	 * @param PriorityItem         
	 * 
	 */

	public void recordPriority(PriorityItem root) {
		if (recordOcc.containsKey(root.priority)) {
			recordOcc.put(root.priority, recordOcc.get(root.priority) + 1);
		} else {
			recordOcc.put(root.priority, 1);
		}
		// sort the map according to desc order of it's values
		recordOcc = sortByValue(recordOcc);
	}


	/**
	 * 
	 * Sort a map according to values in desc. order.
	 * @param < K > the key of the map.         
	 * @param < V > the value to sort according to.  
	 * @param map the map to sort.
	 * @return a map sorted on the values.
	 * 
	 */

	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * Transform queue to array and sort array using fifoComparator
	 * check if there exists and item with priortiy equal to next burstpriority
	 * if yes, remove the item from queue and return the priority item
	 * if not, return null
	 * @param nextBurstItem        
	 * @return PriorityItem 
	 * 
	 */
	public PriorityItem containsPriority(int nextBurstItem) {
		PriorityItem[] temp = toArray();
		Arrays.sort(temp, fifoComparator);

		for (int i = 0; i < temp.length; i++) {
			if (temp[i].priority == nextBurstItem) {
				priorityQueue.remove(temp[i]);
				return temp[i];
			}
			else if(temp[i].priority > nextBurstItem)
			{
				return null;
			}
		}
		return null;
	}
  
	/**
	 * helper function to convert PriorityQueue to Array
	*/
	public PriorityItem[] toArray() {
		return priorityQueue.toArray(new PriorityItem[priorityQueue.size()]);
	}
	
	/**
	 * comparator to order items by asc. order of priority (1,2,3...) 
	 * and by asc. order of time stamp, if priorities are same (to maintain fifo).
	*/

	public static Comparator<PriorityItem> fifoComparator = new Comparator<PriorityItem>() {

		@Override
		public int compare(PriorityItem o1, PriorityItem o2) {

			if (o1.priority != o2.priority) {
				return o1.priority - o2.priority;
			} else {
				return o1.timeStamp - o2.timeStamp;
			}
		}
	};
}
