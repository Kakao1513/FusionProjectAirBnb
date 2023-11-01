package view;

import java.util.List;

public class View<E> {
	public void printAll(List<E> DTOs) {
		for (E DTO : DTOs) {
			System.out.println("DTO = " + DTO.toString());
		}
	}
}
