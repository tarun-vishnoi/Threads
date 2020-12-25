package CountDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * :-> CountDownLatch is used to make sure that a task waits for other threads
 * before it starts.
 * 
 * Output - { Thread-0 finished. Thread-1 finished. Thread-2 finished. Thread-3
 * finished. All needed threads are up and running... Hence, starting main
 * thread. Thread-4 finished. }
 * 
 * Note : countDown() method decrements the count and await() method blocks
 * until count == 0
 * 
 * @author Tarun Vishnoi
 *
 */
public class CountDownLatchSample {
	public static void main(String[] args) {
		CountDownLatch countDownLatch = new CountDownLatch(4);
		Worker first = new Worker(1000, countDownLatch, "WORKER-1");
		Worker second = new Worker(2000, countDownLatch, "WORKER-2");
		Worker third = new Worker(3000, countDownLatch, "WORKER-3");
		Worker fouth = new Worker(4000, countDownLatch, "WORKER-4");
		Worker fifth = new Worker(5000, countDownLatch, "WORKER-5");

		first.start();
		second.start();
		third.start();
		fouth.start();
		fifth.start();

		try {
			// wait until countDownLatch reduces to 0.
			countDownLatch.await();
			System.out.println("All needed threads are up and running... Hence, starting main thread.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Worker extends Thread {
	int delay;
	CountDownLatch latch;
	String name;

	public Worker(int delay, CountDownLatch latch, String name) {
		this.delay = delay;
		this.latch = latch;
		this.name = name;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(delay);
			latch.countDown();
			System.out.println(Thread.currentThread().getName() + " finished.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}