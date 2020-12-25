package InterThreadCommunication;

public class SynchronizedMethod {

	public static void main(String[] args) {

		Print print = new Print();
		SynchronizedThreads thread1 = new SynchronizedThreads(print);
		SynchronizedThreads thread2 = new SynchronizedThreads(print);
		thread1.start();
		thread2.start();
	}
}

class SynchronizedThreads extends Thread {

	Print print;

	public SynchronizedThreads(Print print) {
		this.print = print;
	}

	@Override
	public void run() {
		try {
			print.print();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class Print {
	public synchronized void print() throws InterruptedException {
		for (int i = 0; i < 3; i++) {
			System.out.println(i);
		}
		Thread.sleep(2000);
	}
}
