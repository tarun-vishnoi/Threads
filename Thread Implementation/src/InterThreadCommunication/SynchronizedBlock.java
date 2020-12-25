package InterThreadCommunication;

import java.util.ArrayList;
import java.util.List;

public class SynchronizedBlock {

	public static void main(String[] args) {

		SynchronizedThread thread1 = new SynchronizedThread("Tarun");
		SynchronizedThread thread2 = new SynchronizedThread("Alex");
		thread1.start();
		thread2.start();
	}

}

class SynchronizedThread extends Thread {
	Employee employee = new Employee();
	List<String> employees = new ArrayList<String>();

	public SynchronizedThread(String name) {
		employee.name = name;
	}

	@Override
	public void run() {
		employee.empName(employee.name, employees);
	}
}

class Employee {
	String name = "";
	public static int count = 0;

	public void empName(String emp, List<String> employees) {

		// Only one thread is permitted to change emp's name at a time.
		synchronized (this) {
			name = emp;
			count++;
		}
		System.out.println("Name -> " + name + " : Count -> " + count);

		// All other threads are permitted to add emp name into list
		employees.add(emp);
		employees.forEach(name -> System.out.println("List -> " + name));
	}
}
