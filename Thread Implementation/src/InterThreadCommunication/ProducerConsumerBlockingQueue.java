package InterThreadCommunication;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Output |
 * 
 * Queue entries -> [0]
 * 
 * Produced value - > 0
 * 
 * Queue entries -> [0, 1]
 * 
 * Produced value - > 1
 * 
 * Consumed value -> 0
 * 
 * Queue entries -> [1, 2]
 * 
 * Produced value - > 2
 * 
 * Consumed value -> 1
 * 
 * Queue entries -> [2, 3]
 * 
 * Produced value - > 3
 * 
 * Queue entries -> [3, 4]
 * 
 * Produced value - > 4
 * 
 * Consumed value -> 2
 * 
 * Consumed value -> 3
 * 
 * Queue entries -> [4, 5]
 * 
 * Produced value - > 5
 * 
 * Consumed value -> 4
 * 
 * Queue entries -> [5, 6]
 * 
 * Produced value - > 6 }
 * 
 * @author Tarun Vishnoi
 *
 */
public class ProducerConsumerBlockingQueue {
	public static void main(String[] args) throws InterruptedException {

		BlockingQueue<Integer> queue = new LinkedBlockingQueue<Integer>(2);

		Thread producer = new Thread(() -> {
			int value = 0;
			while (true) {
				try {
					queue.put(value);
					System.out.println("Queue entries -> " + queue);

					System.out.println("Produced value - > " + value);
					value++;
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		Thread consumer = new Thread(() -> {
			while (true) {
				int value;
				try {
					Thread.sleep(1000);
					value = queue.take();
					System.out.println("Consumed value -> " + value);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		producer.start();
		consumer.start();

		producer.join();
		consumer.join();
	}
}
