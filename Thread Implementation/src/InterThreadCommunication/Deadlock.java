package InterThreadCommunication;

/**
 * How to avoid Deadlock : Avoid Nested Locks, Lock Only What is Required, Avoid
 * waiting indefinitely.
 * 
 * @author Tarun Vishnoi
 *
 */
public class Deadlock {
	public static void main(String[] args) {
		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();
		Thread t1 = new Thread(new ThreadExample(obj1, obj2), "t1");
		Thread t2 = new Thread(new ThreadExample(obj2, obj3), "t2");
		Thread t3 = new Thread(new ThreadExample(obj3, obj1), "t3");

		t1.start();
		t2.start();
		t3.start();
	}
}

class ThreadExample implements Runnable {
	Object obj1;
	Object obj2;

	public ThreadExample(Object obj1, Object obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
	}

	@Override
	public void run() {
		String name = Thread.currentThread().getName();
		System.out.println(name + " acquiring lock on " + obj1);
		synchronized (obj1) {
			System.out.println(name + " acquired lock on " + obj1);
			work();
			System.out.println(name + " acquiring lock on " + obj2);
			synchronized (obj2) {
				System.out.println(name + " acquired lock on " + obj2);
				work();
			}
			System.out.println(name + " released lock on " + obj2);
		}
		System.out.println(name + " released lock on " + obj1);
		System.out.println("Work finished...");
	}

	public void work() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}