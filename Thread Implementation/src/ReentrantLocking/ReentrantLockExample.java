package ReentrantLocking;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Java Lock API provides more visibility and options for locking, unlike
 * synchronized where a thread might end up waiting indefinitely for the lock,
 * we can use tryLock() to make sure thread waits for specific time only.
 * 
 * Synchronization code is much cleaner and easy to maintain whereas with Lock
 * we are forced to have try-finally block to make sure Lock is released even if
 * some exception is thrown between lock() and unlock() method calls.
 * 
 * synchronization blocks or methods can cover only one method whereas we can
 * acquire the lock in one method and release it in another method with Lock
 * API.
 * 
 * synchronized keyword doesn’t provide fairness whereas we can set fairness to
 * true while creating ReentrantLock object so that longest waiting thread gets
 * the lock first.
 * 
 * We can create different conditions for Lock and different thread can await()
 * for different conditions.
 * 
 * 
 * Locks can be implemented across the methods, you can invoke lock() in method1
 * and invoke unlock() in method2.
 * 
 * 1) Ability to lock interruptibly. 2) Ability to timeout while waiting for
 * lock. 3) Power to create fair lock. 4) API to get list of waiting thread for
 * lock. 5) Flexibility to try for lock without blocking.
 * 
 * 
 * @author Tarun Vishnoi
 *
 */
public class ReentrantLockExample {

	public static void main(String[] args) {
		ReentrantLock rel = new ReentrantLock();
		ExecutorService pool = Executors.newFixedThreadPool(4);
		Runnable w1 = new Worker("Job1", rel);
		Runnable w2 = new Worker("Job2", rel);
		Runnable w3 = new Worker("Job3", rel);
		Runnable w4 = new Worker("Job4", rel);
		pool.execute(w1);
		pool.execute(w2);
		pool.execute(w3);
		pool.execute(w4);
		pool.shutdown();
	}
}

class Worker implements Runnable {

	String name;
	ReentrantLock lock;

	public Worker(String name, ReentrantLock lock) {
		this.name = name;
		this.lock = lock;
	}

	@Override
	public void run() {
		while (true) {
			// getting Outer Lock : returns True if lock is free
			boolean res = lock.tryLock();
			if (res) {
				try {
					System.out.println(name + " outer lock acquired.");
					Thread.sleep(1000);
					lock.lock(); // getting Inner Lock

					try {
						System.out.println(name + " inner lock acquired.");
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						System.out.println(name + " inner lock releasing.");
						lock.unlock(); // released inner lock
					}

					System.out.println("Lock Hold Count - " + lock.getHoldCount());
					System.out.println(name + " inner lock released.");

				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					System.out.println(name + " outer lock releasing.");
					lock.unlock(); // released outer lock
				}

				System.out.println("Lock Hold Count - " + lock.getHoldCount());
				System.out.println(name + " outer lock released.");
			}
		}
	}
}