package view;

import persistence.dto.ReservationDTO;

import java.time.LocalDate;
import java.util.List;

public class ReservationView extends View<ReservationDTO> {

	public LocalDate getReservationDate() {
		System.out.println("예약할 날짜를 입력하세요 ");
		System.out.print("년(ex. 2023) : ");
		int year = readInt();
		System.out.print("월(ex. 11) : ");
		int month = readInt();

		return LocalDate.of(year, month, 1);

	}


	/**
	 * 달력 찍어주는 메서드
	 */
	public void displayReservationCalendar(LocalDate date, int capacity, List<ReservationDTO> reservationDTOS) {
		int startDayOfWeek = date.getDayOfWeek().getValue();

		// 해당 월의 마지막 날짜를 구합니다.
		int lastDayOfMonth = date.lengthOfMonth();

		int[] roomCount = new int[lastDayOfMonth + 1];

		for (ReservationDTO dto : reservationDTOS) {
			int start = dto.getCheckIn().getDayOfMonth();
			int end = dto.getCheckOut().getDayOfMonth();

			for (int i = start; i <= end; i++) {
				roomCount[i] += 1;
			}
		}

		System.out.println("\t\t\t << " + date.getYear() + "년 " + date.getMonthValue() + "월 >>");
		System.out.println("  Su    Mo    Tu    We    Th    Fr    Sa");

		// 첫 주 전까지 공백을 출력합니다.
		for (int i = 1; i < startDayOfWeek; i++) {
			System.out.print("      ");
		}

		// 날짜를 출력합니다.
		for (int day = 1; day <= lastDayOfMonth; day++) {
			char status = '^';
			if (roomCount[day] == 0) status = 'O';
			else if (roomCount[day] >= capacity) status = '*';

			System.out.printf("%2d(%c) ", day, status);
			if ((startDayOfWeek + day - 1) % 7 == 0) {
				System.out.println();
			}
		}
		System.out.println();
	}

	public void displayReservations(List<ReservationDTO> reservationDTOS) {
		System.out.println("===========================================예약 리스트=========================================");
		System.out.println("|예약 번호|    유저ID    |    숙소ID   |     예약 신청 시간     | CheckIn | CheckOut |  총요금  |");
		int i = 1;
		for (ReservationDTO dto : reservationDTOS) {
			System.out.printf("|%-7d|%-12d|%-12d|%-15s|%-10s|%-10s|%-10s|\n", i, dto.getUserID(), dto.getAccommodationID(),dto.getReserveDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getCharge());
			i++;
		}
		System.out.println("==============================================================================================");
	}

	public int readReservationIndex(List<ReservationDTO> reservationDTOList) {
		int select = 0;
		while (true) {
			System.out.print("예약 번호를 입력하세요:");
			select = Integer.parseInt(SCANNER.nextLine());
			if (0 < select && select <= reservationDTOList.size()) {
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		return select - 1;
	}
}