package priorityqueue;

// Each item consists of a value, priority and also a auto incremented timestamp 
public class PriorityItem {

	 String value;
	 int priority;
	 int timeStamp;

	/** Constructor **/
	public PriorityItem(String value, int priority) {
		this.value = value;
		this.priority = priority;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public int getTimeStamp() {
		return timeStamp;
	}

	 public int getPriority() {
		return priority;
	}

	/** toString() **/
	public String toString() {
		return "Value Name : " + value + "\nPriority : " + priority;
	}

}
