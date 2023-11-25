import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Random;

public class MyThreadTest{

	@Test
	public void myTest(){
		String temp = "2023-01-01";
		System.out.println( LocalDate.parse(temp));
	}

}
