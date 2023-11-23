package view;

import java.util.List;
import java.util.Scanner;

public class View<E> {

	protected final Scanner sc = new Scanner(System.in);
	public void print(E DTO){
		System.out.println("DTO = " + DTO.toString());
	}
	public void printAll(List<? extends E> DTOs) {
		for (E DTO : DTOs) {
			System.out.println("DTO = " + DTO.toString());
		}
	}
}
