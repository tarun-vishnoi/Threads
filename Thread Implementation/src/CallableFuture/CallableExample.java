package CallableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CallableExample {

	public static void main(String[] args) {

		ExecutorService executor = Executors.newFixedThreadPool(2);

		Callable<Laptop> laptop1 = new Laptop("Dell", "i3");
		Callable<Laptop> laptop2 = new Laptop("Samsung", "i5");
		Callable<Laptop> laptop3 = new Laptop("HP", "i7");
		Callable<Laptop> laptop4 = new Laptop("Asus", "i9");

		Future<Laptop> task1 = executor.submit(laptop1);
		Future<Laptop> task2 = executor.submit(laptop2);
		Future<Laptop> task3 = executor.submit(laptop3);
		Future<Laptop> task4 = executor.submit(laptop4);

		System.out.println("Checking status for task1 :: before -> " + task1.isDone());
		System.out.println("Checking status for task2 :: before -> " + task2.isDone());
		System.out.println("Checking status for task3 :: before -> " + task3.isDone());
		System.out.println("Checking status for task4 :: before -> " + task4.isDone());

		List<Future<Laptop>> tasks = Arrays.asList(task1, task2, task3, task4);

		tasks.forEach(f -> {
			try {
				System.out.println(f.get());
				System.out.println("Checking status for task1 :: after -> " + f.isDone());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		});
	}
}

class Laptop implements Callable<Laptop> {
	String name;
	String processor;

	public Laptop(String name, String processor) {
		this.name = name;
		this.processor = processor;
	}

	@Override
	public Laptop call() throws Exception {
		Thread.sleep(1000);
		return new Laptop(name, processor);
	}

	@Override
	public String toString() {
		return "Laptop [name=" + name + ", processor=" + processor + "]";
	}

}