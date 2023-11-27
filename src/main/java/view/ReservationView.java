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

	public LocalDate getCheckIn() {
		System.out.println("체크인 날짜를 입력하세요 ");
		System.out.print("년(ex. 2023) : ");
		int year = readInt();
		System.out.print("월(ex. 11) : ");
		int month = readInt();
		System.out.print("일(ex. 11) : ");
		int day = readInt();
		return LocalDate.of(year, month, day);
	}

	public LocalDate getCheckOut() {
		System.out.println("체크아웃 날짜를 입력하세요 ");
		System.out.print("년(ex. 2023) : ");
		int year = readInt();
		System.out.print("월(ex. 11) : ");
		int month = readInt();
		System.out.print("일(ex. 11) : ");
		int day = readInt();
		return LocalDate.of(year, month, day);
	}



	/**
	 * 달력 찍어주는 메서드
	 */
	public void displayReservationCalendar(LocalDate date, int capacity, List<ReservationDTO> reservationDTOS) {
		if (reservationDTOS != null) {
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
			System.out.println();
			System.out.println("\t\t\t << " + date.getYear() + "년 " + date.getMonthValue() + "월 >>");
			System.out.println("  Su    Mo    Tu    We    Th    Fr    Sa");

			// 첫 주 전까지 공백을 출력합니다.
			for (int i = 1; i < startDayOfWeek; i++) {
				System.out.print("      ");
			}

			// 날짜를 출력합니다.
			for (int day = 1; day <= lastDayOfMonth; day++) {
				char status = '^';
				if (roomCount[day] == 0) status = '*';
				else if (roomCount[day] >= capacity) status = 'O';

				System.out.printf("%2d(%c) ", day, status);
				if ((startDayOfWeek + day - 1) % 7 == 0) {
					System.out.println();
				}
			}
			System.out.println();
		}else{
			System.out.println("예약정보가 없습니다.");
		}
	}

	public void displayReservations(List<ReservationDTO> reservationDTOS) {
		if (reservationDTOS.isEmpty()) {
			System.out.println("예약 내역이 없습니다.");
			return;
		} else {
			System.out.println("====================================================예약 리스트=================================================");
			System.out.println("|예약 번호|   유저ID    |   숙소ID   |       예약 신청 시간      |  CheckIn  | CheckOut |   총요금  |   예약 상태   |");
			int i = 1;
			for (ReservationDTO dto : reservationDTOS) {
				System.out.printf("|%-8d|%-12d|%-12d|%-25s|%-10s|%-10s|%-10s|%-12s|\n", i, dto.getUserID(), dto.getAccommodationID(), dto.getReserveDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getCharge(), dto.getReservationInfo());
				i++;
			}
			System.out.println("===============================================================================================================");
		}
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

	public void displayReservationInfo(ReservationDTO reservationDTO) {
		System.out.println("====================================================예약 정보=================================================");
		System.out.println("|예약 번호|   유저ID    |   숙소ID   |       예약 신청 시간      |  CheckIn  | CheckOut |   총요금  |   예약 상태   |");
		System.out.printf("|%-8d|%-12d|%-12d|%-20s|%-10s|%-10s|%-10s|%-12s|\n", reservationDTO.getReservationID(), reservationDTO.getUserID(), reservationDTO.getAccommodationID(), reservationDTO.getReserveDate(), reservationDTO.getCheckIn(), reservationDTO.getCheckOut(), reservationDTO.getCharge(), reservationDTO.getReservationInfo());
		System.out.println("===============================================================================================================");
	}
}
