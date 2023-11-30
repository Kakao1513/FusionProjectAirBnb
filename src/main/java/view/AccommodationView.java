package view;

import Enums.AccommodationStatus;
import persistence.dto.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccommodationView extends View<AccommodationDTO> {
	private static final Scanner SCANNER = new Scanner(System.in);

	public int readAccomIndex(List<AccommodationDTO> myAccomList) {
		if (myAccomList.isEmpty()) {
			System.out.println("등록된 숙소가 없습니다.");
			return -1;
		}
		int select = 0;
		while (true) {
			System.out.print("숙소 번호를 입력하세요:");
			select = Integer.parseInt(SCANNER.nextLine());
			if (0 < select && select <= myAccomList.size()) {
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		return select - 1;
	}

	public void displayAccomList(List<AccommodationDTO> accommodationDTOS) {
		System.out.println("=========================================숙소 리스트=================================================================================");
		System.out.println("|번호|      이름      |          주소          |  타입  | 인원 |             설명             |                  승인상태               |");
		System.out.println("------------------------------------------------------------------------------------------=========================================");
		int i = 1;
		for (AccommodationDTO dto : accommodationDTOS) {
			System.out.printf("|%-4d|%-16s|%-25s|%-8s|%-6s|%-30s|%-34s|\n", i, dto.getAccomName(), dto.getAddress(), dto.getType(), dto.getCapacity(), dto.getComment(), dto.getStatus());
			i++;
		}
		System.out.println("====================================================================================================================================");
	}

	public void displayAccomListCountOrder(List<AccommodationDTO> accommodationDTOS) {
		System.out.println("===================================================================================나의 숙소 목록================================================================");
		System.out.println("| 번호 |       이름       |                  주소                  |     타입     |  인원  |                      설명                      |         승인상태      |");
		System.out.println("----------------------------------------------------------------------------------------------------------------------------------------------------------------");
		int i = 1;
		for (AccommodationDTO dto : accommodationDTOS) {
			System.out.printf("| %-4d | %-15s | %-40s | %-10s | %-6s | %-40s | %-20s |\n", i, dto.getAccomName(), dto.getAddress(), dto.getType(), dto.getCapacity(), dto.getComment(), dto.getStatus());
			i++;
		}
		System.out.println("================================================================================================================================================================");
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
		System.out.println("2. 숙소 타입으로 검색");
		System.out.println("3. 완료");
		System.out.println("============================");
		return rangeSelect(1, 3);
	}

	public void displayAppliedFilters(Map<String, Object> filters) {
		System.out.println("==========적용된 필터==========");
		if (filters.get("accomName") != null) {
			System.out.println("숙소 이름 : " + filters.get("accomName"));
		}
		if (filters.get("checkIn") != null && filters.get("checkOut") != null) {
			System.out.println("검색 날짜 : " + filters.get("checkIn") + " ~ " + filters.get("checkOut"));
		}
		if (filters.get("capacity") != null) {
			System.out.println("인원 수 : " + filters.get("capacity"));
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
		return switch (accomType) {
			case 1 -> "개인실";
			case 2 -> "공간 전체";
			default -> throw new IllegalStateException("Unexpected value: " + accomType);
		};
	}

	public int getHeadcount() {
		System.out.println("예약할 인원수를 입력하세요 ");
		return readInt();
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

		System.out.printf("=============[%3d] %s============\n", accomDTO.getAccomID(), accomDTO.getAccomName());
		System.out.println("위치 : " + accomDTO.getAddress());
		System.out.println("설명 : " + accomDTO.getComment());
		System.out.printf("숙박요금 : [평일] %d, [주말] %d\n", rateDTO.getWeekday(), rateDTO.getWeekend());
		System.out.println("================================");
	}


	public void displayAmenityByType(List<AmenityDTO> amenityDTOS, String type) {
		System.out.printf("=========%s 편의시설 리스트=========\n", type);
		for (AmenityDTO dto : amenityDTOS) {
			System.out.println(dto.getName());
		}
		System.out.println("================================");
	}

	public void displayAmenity(List<AmenityDTO> amenityDTOS) {
		System.out.println("==========편의시설 리스트==========");
		if (!amenityDTOS.isEmpty()) {
			for (AmenityDTO dto : amenityDTOS) {
				System.out.println(dto.getName());
			}
		}
		else {
			System.out.println("등록된 편의시설이 없습니다.");
		}
		System.out.println("=================================");
	}



	// 1. 숙박 등록 신청(이름, 숙소 소개, 객실 타입(공간 전체/개인실), 수용 정보, 편의시설)
	public AccommodationDTO getAccomInfoFromUser(UserDTO currentUser) {
		System.out.print("숙소 이름을 입력하세요: ");
		String accomName = SCANNER.nextLine();

		System.out.print("숙소 주소를 입력하세요: ");
		String address = SCANNER.nextLine();

		String type = getAccomTypeFromUser();

		System.out.print("수용 인원을 입력하세요: ");
		int capacity = readInt();

		System.out.print("숙소 설명을 입력하세요: ");
		String comment = SCANNER.nextLine();

		return AccommodationDTO.builder()
				.userID(currentUser.getUserId())
				.accomName(accomName)
				.address(address)
				.type(type)
				.capacity(capacity)
				.comment(comment)
				.status(String.valueOf(AccommodationStatus.Waiting))
				.build();
	}

	// 2. 요금 정책 설정(주말요금/평일요금 일괄 설정)
	public RatePolicyDTO getRatePolicyFromUser(int accomID) {
		System.out.print("설정할 평일 요금을 입력하세요: ");
		int weekday = readInt();
		System.out.print("설정할 주말 요금을 입력하세요: ");
		int weekend = readInt();

		return RatePolicyDTO.builder()
				.accomID(accomID)
				.weekday(weekday)
				.weekend(weekend)
				.build();
	}

	// 3. 할인 정책 설정(연박 할인 적용 기간 설정, 정량/정률 설정, 이전 예약 건에 대해서도 할인 요금 적용 여부 보이기)
	public DiscountPolicyDTO getDiscountFromUser(AccommodationDTO accomDTO) {
		System.out.print("할인 타입을 입력하세요 [1.정량 2.정률] : ");
		int type = readInt();
		System.out.print("할인 시작 날짜  (yyyy-mm-dd) : ");
		LocalDate startDate = LocalDate.parse(SCANNER.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		System.out.print("할인 종료 날짜  (yyyy-mm-dd) : ");
		LocalDate endDate = LocalDate.parse(SCANNER.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		if (type == 1) {
			System.out.print("할인 금액을 입력하세요 (n원): ");
		} else {
			System.out.println("할인 비율을 입력하세요 (n%) : ");
		}
		int value = readInt();

		return DiscountPolicyDTO.builder()
				.accomID(accomDTO.getAccomID())
				.startDate(startDate)
				.endDate(endDate)
				.discountType(type == 1 ? "정량" : "정률")
				.value(value)
				.build();
	}

	public YearMonth readYearMonth() {
		System.out.print("연-월을 입력하세요 (ex. 2023-11)");
		String ym = SCANNER.nextLine();
		return YearMonth.parse(ym);
	}


}
