package view;

import persistence.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccommodationView extends View<AccommodationDTO> {
	private static final Scanner SCANNER = new Scanner(System.in);

/*	public int displayAccomMenu() {
		System.out.println("=========숙소 메뉴=========");
		System.out.println("(1) 숙소 예약");
		System.out.println("(2) 숙소 등록");
		System.out.println("(3) 숙소 요금 설정");
		System.out.println("(4) 숙소 할인 설정");
		System.out.println("(5) 나가기");
		System.out.println("==========================");

		System.out.print("선택할 번호를 입력하세요 : ");
		return readInt();
	}*/

	public void displayAccomList(List<AccommodationDTO> accommodationDTOS) {
		System.out.println("=========================================숙소 리스트========================================");
		System.out.println("|번호|      이름      |          주소          |  타입  | 인원 |             설명             |");
		System.out.println("------------------------------------------------------------------------------------------");
		for (AccommodationDTO dto : accommodationDTOS) {
			System.out.printf("|%-3d|%-14s|%-20s|%-7s|%-5s|%-25s|\n", dto.getAccomId(), dto.getAccomName(), dto.getAddress(), dto.getType(), dto.getCapacity(), dto.getComment());
		}
		System.out.println("===========================================================================================");
	}


	public int getOrder() {
		System.out.println("1: 상세보기, 2: 필터로 검색");
		System.out.print("선택할 번호를 입력하세요 : ");
		return SCANNER.nextInt();
	}

	public int getDailyOrDiscount() {
		System.out.println("1: 일별 요금 설정, 2: 할인 정책 설정");
		System.out.println("선택할 번호를 입력하세요 : ");
		return readInt();
	}

	public int getAccomNumberFromUser() {
		System.out.print("선택할 숙소 번호를 입력하세요 : ");
		return readInt();
	}

	public int displayFilterList() {
		System.out.println("==========검색 필터==========");
		System.out.println("1. 숙소 이름으로 검색");
		System.out.println("2. 날짜로 검색");
		System.out.println("3. 인원 수로 검색");
		System.out.println("4. 숙소 타입으로 검색");
		System.out.println("5. 완료");
		System.out.println("============================");
		return rangeSelect(1, 5);
	}

	public void displayAppliedFilters(Map<String, Object> filters) {
		System.out.println("==========적용된 필터==========");
		if (filters.get("accomName") != null) {
			System.out.println("숙소 이름 : " + filters.get("accomName"));
		}
		if (filters.get("period") != null) {
			String[] period = (String[]) filters.get("period");
			System.out.println("검색 날짜 : " + period[0] + " ~ " + period[1]);
		}
		if (filters.get("capacity") != null) {
			System.out.println("최소 수용 인원 : " + filters.get("capacity"));
		}
		if (filters.get("accomType") != null) {
			System.out.println("숙소 타입 : " + filters.get("accomType"));
		}
	}

	public String getAccomNameFromUser() {
		String accomName;
		System.out.print("숙소 이름 : ");
		accomName = SCANNER.nextLine();

		return accomName;
	}

	public String getCapacityFromUser() {
		String capacity;
		System.out.print("수용 인원 : ");
		capacity = SCANNER.nextLine();

		return capacity;
	}

	public String getAccomTypeFromUser() {
		int accomType;
		System.out.print("숙소 타입 1.개인실 2.공간 전체 : ");
		accomType = readInt();
		return switch (accomType){
			case 1 -> "개인실";
			case 2 -> "공간 전체";
			default -> throw new IllegalStateException("Unexpected value: " + accomType);
		};
	}


	public String[] getPeriodFromUser() {
		String startDate, endDate;
		System.out.print("시작 날짜 (ex. 2023-10-01) : ");
		startDate = SCANNER.nextLine();
		System.out.print("끝 날짜 (ex. 2023-10-30) : ");
		endDate = SCANNER.nextLine();

		return new String[]{startDate, endDate};
	}

	public void displayAccomInfo(AccommodationDTO accomDTO, RatePolicyDTO rateDTO) {

		System.out.printf("==========[%3d] %s==========\n", accomDTO.getAccomId(), accomDTO.getAccomName());
		System.out.println("위치 : " + accomDTO.getAddress());
		System.out.println("설명 : " + accomDTO.getComment());
		System.out.printf("숙박요금 : [평일] %d, [주말] %d\n", rateDTO.getWeekday(), rateDTO.getWeekend());
		System.out.println("================================");
	}

	public void displayAmenity(List<AmenityDTO> amenityDTOS) {
		System.out.println("=========편의시설 리스트=========");
		for (AmenityDTO dto : amenityDTOS) {
			System.out.println(dto.getName());
		}
		System.out.println("================================");
	}

	public void displayReservations(List<ReservationDTO> reservationDTOS) {
		System.out.println("===========예약 리스트===========");
		for (ReservationDTO dto : reservationDTOS) {
			System.out.println(dto.getCheckIn() + " ~ " + dto.getCheckOut());
		}
		System.out.println("================================");
	}

	public void displayReviews(List<ReviewDTO> reviewDTOS) {
		System.out.println("===========리뷰 리스트===========");
		for (ReviewDTO dto : reviewDTOS) {
			System.out.printf("[%d] %d : %s\n", dto.getRate(), dto.getUserID(), dto.getText());
		}
		System.out.println("================================");
	}

	public LocalDate getReservationDate() {
		System.out.println("예약할 날짜를 입력하세요 ");
		System.out.print("년(ex. 2023) : ");
		int year = readInt();
		System.out.print("월(ex. 11) : ");
		int month = readInt();

		return LocalDate.of(year, month, 1);

	}

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

		System.out.println("<< " + date.getYear() + "년 " + date.getMonth() + "월 >>");
		System.out.println("Su    Mo    Tu    We    Th    Fr    Sa");

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

	public AccommodationDTO getAccomInfoFromUser() {
		System.out.print("숙소 이름을 입력하세요: ");
		String accomName = SCANNER.nextLine();

		System.out.print("숙소 주소를 입력하세요: ");
		String address = SCANNER.nextLine();

		String type = getAccomTypeFromUser();

		System.out.print("수용 인원을 입력하세요: ");
		int capacity = readInt();

		System.out.print("숙소 설명을 입력하세요: ");
		String comment = SCANNER.nextLine();

		return AccommodationDTO.builder().userID(1).accomName(accomName).address(address).type(type).capacity(capacity).comment(comment).status("승인 대기중").build();
	}

	public RatePolicyDTO getRatePolicyFromUser(int accomID) {
		System.out.println("설정할 평일 요금을 입력하세요: ");
		int weekday = readInt();
		System.out.println("설정할 주말 요금을 입력하세요: ");
		int weekend = readInt();

		return RatePolicyDTO.builder().accomID(accomID).weekday(weekday).weekend(weekend).build();
	}

	public void Return() {
		System.out.println("(0 입력시 이전 메뉴로 돌아갑니다.)");
	}


}
