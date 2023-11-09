package view;

import java.util.List;

public class View<E> {

	public void print(E DTO){
		System.out.println("DTO = " + DTO.toString());
	}
	public void printAll(List<? extends E> DTOs) {
		for (E DTO : DTOs) {
			System.out.println("DTO = " + DTO.toString());
		}
	}
}
