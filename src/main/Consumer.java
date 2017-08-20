package main;

import priorityqueue.ThreadSafePriorityQueue;

/**
 * Consumer thread class is used to dequeue
 * If queue is empty, the thread goes to wait state, until notified by producer
 * The highest priority item is dequeued by calling dequeue() method of ThreadSafePriorityQueue
 * After dequeue, thread goes to wait state again, to pass control back to producer
*/
class Consumer extends Thread {
	private ThreadSafePriorityQueue queue;
	boolean flag = true;

	public Consumer(ThreadSafePriorityQueue queue) {

		this.queue = queue;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (queue) {

				while (queue.isEmpty()) {
					System.out.println("Queue is empty," + "Consumer thread is waiting"
							+ " for producer thread to put something in queue");
					queue.notifyAll();
					try {
						queue.wait();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}

				try {
					System.out.println("Consuming value : " + queue.dequeue());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				queue.notifyAll();
				try {
					queue.wait();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

		}
	}

}
