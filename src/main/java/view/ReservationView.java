package view;

import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;

public class ReservationView extends View<ReservationDTO> {

	public LocalDate getReservationDate() {
		System.out.println("확인할 날짜를 입력하세요 ");
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

			LocalDate firstDate = LocalDate.of(date.getYear(), date.getMonth(), 1);
			LocalDate lastDate = LocalDate.of(date.getYear(), date.getMonth(), lastDayOfMonth);

			int[] roomCount = new int[lastDayOfMonth + 1];

			for (ReservationDTO dto : reservationDTOS) {
				LocalDate startDate = dto.getCheckIn();
				LocalDate endDate = dto.getCheckOut().minusDays(1);

				int start = startDate.getDayOfMonth();
				int end = endDate.getDayOfMonth();

				if(startDate.isBefore(firstDate)){
					start = 1;
				}
				if(endDate.isAfter(lastDate)){
					end = lastDayOfMonth;
				}

				for (int i = start; i <= end; i++) {
					roomCount[i] += dto.getHeadcount();
				}
			}
			System.out.println();
			System.out.println("\t\t\t << " + date.getYear() + "년 " + date.getMonthValue() + "월 >>");
			System.out.println("  Su    Mo    Tu    We    Th    Fr    Sa");

			// 첫 주 전까지 공백을 출력합니다.
			for (int i = 0; i < startDayOfWeek; i++) {
				System.out.print("      ");
			}

			// 날짜를 출력합니다.
			for (int day = 1; day <= lastDayOfMonth; day++) {
				char status = '^';
				if (roomCount[day] == 0) status = '*';
				else if (roomCount[day] >= capacity) status = 'O';

				System.out.printf("%2d(%c) ", day, status);
				if ((startDayOfWeek + day) % 7 == 0) {
					System.out.println();
				}
			}
			System.out.println();
		}else{
			System.out.println("예약정보가 없습니다.");
		}
	}

	public void displayReservations(List<ReservationDTO> reservationDTOS, List<AccommodationDTO> accomNames, List<UserDTO> userNames) {
		if (reservationDTOS.isEmpty()) {
			System.out.println("예약 내역이 없습니다.");
			return;
		} else {
			System.out.println("====================================================예약 리스트=================================================");
			System.out.println("|번호|   유저 이름    |   숙소 이름   |       예약 신청 시간      |  CheckIn  | CheckOut |   총요금  |   예약 상태   |");
			int i = 1;
			for (ReservationDTO dto : reservationDTOS) {
					System.out.printf("|%-4d|%-12s|%-12s|%-25s|%-10s|%-10s|%-10s|%-12s|\n", i, userNames.get(i - 1).getName(), accomNames.get(i - 1).getAccomName(), dto.getReserveDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getCharge(), dto.getReservationInfo());

				i++;
			}
			System.out.println("===============================================================================================================");
		}
	}

	public int displayReadyReservations(List<ReservationDTO> reservationDTOS, List<AccommodationDTO> accomNames, List<UserDTO> userNames) {
		if (reservationDTOS.isEmpty()) {
			System.out.println("예약 내역이 없습니다.");
			return -1;
		} else {
			int select = 0;
			System.out.println("====================================================예약 리스트=================================================");
			System.out.println("|번호|   유저 이름    |   숙소 이름   |       예약 신청 시간      |  CheckIn  | CheckOut |   총요금  |   예약 상태   |");
			int i = 1;
			for (ReservationDTO dto : reservationDTOS) {
				if(dto.getReservationInfo().equals("승인대기중") || dto.getReservationInfo().equals("예약중")) {
					System.out.printf("|%-4d|%-12s|%-12s|%-25s|%-10s|%-10s|%-10s|%-12s|\n", i, userNames.get(i - 1).getName(), accomNames.get(i - 1).getAccomName(), dto.getReserveDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getCharge(), dto.getReservationInfo());
				}
				i++;
			}
			System.out.println("===============================================================================================================");
			while (true) {
				System.out.print("예약 번호를 입력하세요:");
				select = Integer.parseInt(SCANNER.nextLine());
				if (0 < select && select <= reservationDTOS.size()) {
					break;
				} else {
					System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
				}
			}
			return select - 1;
		}
	}

	public int readReservationIndex(List<ReservationDTO> reservationDTOList) {
		int select = 0;
		if(reservationDTOList.isEmpty()){
			return -1;
		}
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

	public void displayReservationInfo(ReservationDTO reservationDTO, String accomName, String userName) {
		System.out.println("====================================================예약 정보=================================================");
		System.out.println("|   유저 이름    |   숙소 이름   |       예약 신청 시간      |  CheckIn  | CheckOut |   총요금  |   예약 상태   |");
		System.out.printf("|%-16s|%-16s|%-16s|%-10s|%-10s|%-10s|%-12s|\n", userName, accomName, reservationDTO.getReserveDate(), reservationDTO.getCheckIn(), reservationDTO.getCheckOut(), reservationDTO.getCharge(), reservationDTO.getReservationInfo());
		System.out.println("===============================================================================================================");
	}
	public int selectReserveAccom(List<ReservationDTO> reservationDTOS, List<String> accomNames){
		System.out.println("=======================================숙박 완료된 숙소==========================================");
		System.out.println("|번호|   숙소 이름   |       예약 신청 시간      |  CheckIn  | CheckOut |   총요금  |   예약 상태   |");
		int i = 1;
		for (ReservationDTO dto : reservationDTOS) {
			System.out.printf("|%-4d|%-12s|%-25s|%-10s|%-10s|%-10s|%-12s|\n", i, accomNames.get(i - 1), dto.getReserveDate(), dto.getCheckIn(), dto.getCheckOut(), dto.getCharge(), dto.getReservationInfo());
			i++;
		}
		System.out.println("===============================================================================================");
		int select = 0;
		while (true) {
			System.out.print("예약 번호를 입력하세요:");
			select = readInt();
			if (0 < select && select <= reservationDTOS.size()) {
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		return select - 1;
	}
}
