package main;

import java.util.Scanner;

import priorityqueue.ThreadSafePriorityQueue;

/**
 * Producer thread class provides user input option to enqueue or dequeue
 * If enqueue, then producer calls enqueue(value, priority) method of ThreadSafePriorityQueue
 * If dequeue, thread goes to waiting state, allowing consumer to dequeue
 * If enqueing an item into the queue exceeds the queue capacity, the thread goes to wait state, until notified by consumer
*/
class Producer extends Thread {
	private ThreadSafePriorityQueue queue;
	private int maxSize;

	public Producer(ThreadSafePriorityQueue queue, int maxSize) {
		this.queue = queue;
		this.maxSize = maxSize;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {

				while (queue.size() == maxSize) {
					try {
						System.out.println("Queue is full, " + "Producer thread waiting for "
								+ "consumer to take something from queue");
						queue.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
				Scanner s = new Scanner(System.in);
				System.out.println("Enter 1: Enqueue 2:Dequeue");
				int input = s.nextInt();
				if (input == 1) {
					System.out.println("Enter the item value: ");
					String enqueueItemValue = s.next();
					System.out.println("Enter the item priority: ");
					int enqueueItemPriority = s.nextInt();
					System.out.println("Producing value");
					try {
						queue.enqueue(enqueueItemValue, enqueueItemPriority);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					queue.notifyAll();
				} else if (input == 2) {
					queue.notifyAll();
					try {
						queue.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} 
			}

		}
	}
}
