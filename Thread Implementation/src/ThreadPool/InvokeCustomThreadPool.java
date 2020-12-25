package ThreadPool;

import java.util.concurrent.LinkedBlockingQueue;

public class InvokeCustomThreadPool {
	public static void main(String[] args) {
		CustomThreadPool pool = new CustomThreadPool(4);
		for (int i = 1; i <= 5; i++) {
			Work work = new Work("Work " + i);
			System.out.println("Created : " + work.getName());
			pool.execute(work);
		}
		pool.shutdown();
	}

}

class CustomThreadPool {

	private int poolSize;
	private LinkedBlockingQueue<Runnable> queue;
	private WorkerThread[] workers;

	public CustomThreadPool(int poolSize) {
		this.poolSize = poolSize;
		queue = new LinkedBlockingQueue<Runnable>();
		workers = new WorkerThread[poolSize];
		for (int i = 0; i < poolSize; i++) {
			workers[i] = new WorkerThread();
			workers[i].start();
		}
	}

	private class WorkerThread extends Thread {
		@Override
		public void run() {

			Runnable task;

			while (true) {

				synchronized (queue) {
					while (queue.isEmpty()) {
						try {
							queue.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					task = queue.poll();
				}
				task.run();
			}

		}
	}

	public void execute(Runnable task) {
		synchronized (queue) {
			queue.add(task);
			queue.notify();
		}
	}

	public void shutdown() {
		System.out.println("Shutting down thread pool");
		for (int i = 0; i < poolSize; i++) {
			workers[i] = null;
		}
	}
}

class Work implements Runnable {
	private String name;

	public Work(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void run() {
		System.out.println("Executing : " + name);
	}
}