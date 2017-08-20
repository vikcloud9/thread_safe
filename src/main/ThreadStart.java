package main;

import java.util.Scanner;

import priorityqueue.ThreadSafePriorityQueue;

/**
 * Main thread class that starts producer and consumer threads 
*/
public class ThreadStart {
	public static void main(String args[]) {
        
		Scanner s = new Scanner(System.in);

		System.out.println("Enter the maxSize of the queue");
		int maxSize = s.nextInt();
		System.out.println("Enter the burst rate");
		int burstRate = s.nextInt();
		
		// The user defined burstRate is added as a constructor parameter when creating object for ThreadSafePriorityQueue class.
		// Now PROBLEM A can be generalized to add arbitary burstRate values.
		
		ThreadSafePriorityQueue threadSafePriorityQueue = new ThreadSafePriorityQueue(maxSize, burstRate);
		
		// threadSafePriorityQueue is made a synchronized resource in both threads, to make it thread safe
		
		Producer producer = new Producer(threadSafePriorityQueue, maxSize);	
		Consumer consumer = new Consumer(threadSafePriorityQueue);
		producer.start();    // start producer thread
		consumer.start();    // start consumer thread

	}

}
