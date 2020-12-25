package InterThreadCommunication;

public class PrintOddEven {

	public static void main(String[] args) {
		Number.len = 20;
		Number number = new Number();
		new Thread(() -> {
			try {
				number.printEvenNum();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		new Thread(() -> {
			try {
				number.printOddNum();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

}

class Number {
	int counter = 1;
	public static int len;

	public void printOddNum() throws InterruptedException {
		synchronized (this) {
			while (counter < len) {
				while (counter % 2 == 1) {
					wait();
				}
				System.out.println(counter + " ");
				counter++;
				notify();
			}
		}
	}

	public void printEvenNum() throws InterruptedException {
		synchronized (this) {
			while (counter < len) {
				while (counter % 2 == 0) {
					wait();
				}
				System.out.println(counter + " ");
				counter++;
				notify();
			}
		}
	}
}