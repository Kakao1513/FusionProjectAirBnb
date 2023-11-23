package view;

import java.util.List;
import java.util.Scanner;

public class View<E> {
	protected static final Scanner sc = new Scanner(System.in);

	public void print(E DTO) {
		System.out.println("DTO = " + DTO.toString());
	}

	public void printAll(List<? extends E> DTOs) {
		for (E DTO : DTOs) {
			System.out.println("DTO = " + DTO.toString());
		}
	}

	public static int rangeSelect(int start, int end) {
		int select = 1;
		while (select != 0) {
			System.out.print("Input : ");
			select = Integer.parseInt(sc.nextLine());
			if (start <= select && select <= end) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
		return select;
	}
}
