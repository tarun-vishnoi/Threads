package ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExample {

	public static void main(String[] args) {
		Runnable r1 = new Task(1, 2);
		Runnable r2 = new Task(5, 12);
		Runnable r3 = new Task(51, 21);
		Runnable r4 = new Task(16, 27);
		Runnable r5 = new Task(81, 52);

		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(r1);
		executor.submit(r2);
		executor.execute(r3);
		executor.execute(r4);
		executor.execute(r5);
		System.out.println(executor.isShutdown());
		executor.shutdown();
		System.out.println(executor.isShutdown());

	}

}

class Task implements Runnable {
	int a, b;

	public Task(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		System.out.println(sum(a, b));
	}

	public int sum(int a, int b) {
		return a + b;
	}
}