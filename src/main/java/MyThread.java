import java.util.Random;

public class MyThread extends Thread{
	private static final Random random = new Random();
	@Override
	public void run() {
		String threadName = Thread.currentThread().getName();
		System.out.println("- " + threadName + " has been started");
		int delay = 1000 + random.nextInt(4000);
		try {
			Thread.sleep(delay);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("- " + threadName + " has been ended (" + delay + "ms)");
	}
}
