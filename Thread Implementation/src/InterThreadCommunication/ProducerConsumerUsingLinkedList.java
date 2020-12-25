package InterThreadCommunication;

import java.util.LinkedList;

/**
 * 
 * Output -> { Produced Value -> 0 [0] Consumed Value -> 0 Produced Value -> 1
 * [1] Produced Value -> 2 [1, 2] Consumed Value -> 1 Consumed Value -> 2
 * Produced Value -> 3 [3] Produced Value -> 4 [3, 4] Consumed Value -> 3
 * Consumed Value -> 4 Produced Value -> 5 [5] Produced Value -> 6 [5, 6] }
 * 
 * @author Tarun Vishnoi
 *
 */

public class ProducerConsumerUsingLinkedList {

	public static void main(String[] args) throws InterruptedException {

		ProducerConsumer pc = new ProducerConsumer();
		Thread t1 = new Thread(() -> {
			try {
				pc.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				pc.consume();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		t1.start();
		t2.start();

		t1.join();
		t2.join();
	}
}

class ProducerConsumer {
	LinkedList<Integer> list = new LinkedList<Integer>();
	int capacity = 2;
	int value = 0;

	public void produce() throws InterruptedException {
		while (true) {

			synchronized (this) {
				while (list.size() == capacity) {
					wait();
				}
				System.out.println("Produced Value -> " + value);
				list.add(value++);
				notify(); // notifies the consumer thread that now it can start consuming
				System.out.println(list);
				Thread.sleep(1000);
			}

		}
	}

	public void consume() throws InterruptedException {
		while (true) {
			synchronized (this) {
				while (list.size() == 0) {
					wait();
				}
				System.out.println("Consumed Value -> " + list.removeFirst());
				notify(); // Wakes up producer thread

				Thread.sleep(1000);
			}
		}
	}
}