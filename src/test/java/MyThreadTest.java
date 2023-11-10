import org.junit.jupiter.api.Test;

import java.util.Random;

public class MyThreadTest{

	@Test
	public void test(){
		MyThread thread1 = new MyThread();
		thread1.setName("Thread #1");
		Thread thread2 = new MyThread();
		thread2.setName("Thread #2");

		thread1.start();
		thread2.start();
	}

}
