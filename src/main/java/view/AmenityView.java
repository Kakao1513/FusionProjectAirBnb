package view;

import persistence.dto.AmenityDTO;

import java.util.Arrays;

public class AmenityView extends View<AmenityDTO> {

	public Boolean[] registBasicAmenities() {
		Boolean[] selected = new Boolean[8];
		Arrays.fill(selected, false);
		final int QUIT = 7;
		int command = 0;

		System.out.println("[기본 편의 시설]");
		System.out.println("1. 화장지");
		System.out.println("2. 손과 몸을 씻을 수 있는 비누");
		System.out.println("3. 게스트당 수건 1장");
		System.out.println("4. 침대당 침구 1세트");
		System.out.println("5. 게스트당 베개 1개");
		System.out.println("6. 청소 용품");
		System.out.println("7. 입력 종료");
		System.out.println("------------------------");

		System.out.print("입력 (ex. 1 2 7) : ");
		while (command != QUIT) {
			command = SCANNER.nextInt();
			selected[command] = true;
		}
		SCANNER.nextLine();

		return selected;
	}

	public  Boolean[] registFavoriteAmenities() {
		Boolean[] selected = new Boolean[12];

		Arrays.fill(selected, false);
		final int QUIT = 11;
		int command = 0;

		System.out.println("[게스트가 가장 많이 검색하는 편의 시설]");
		System.out.println("1. 수영장");
		System.out.println("2. 와이파이");
		System.out.println("3. 주방");
		System.out.println("4. 무료 주차 공간");
		System.out.println("5. 자쿠지");
		System.out.println("6. 세탁기 또는 건조기");
		System.out.println("7. 에어컨 또는 난방");
		System.out.println("8. 셀프 체크인");
		System.out.println("9. 노트북 작업 공간");
		System.out.println("10. 반려동물 동반 가능");
		System.out.println("11. 입력 종료");

		System.out.println("------------------------");
		System.out.print("입력 (ex. 1 2 11) : ");
		while (command != QUIT) {
			command = SCANNER.nextInt();
			selected[command] = true;
		}
		SCANNER.nextLine();

		return selected;
	}

	public Boolean[] registSafetyAmenities() {
		Boolean[] selected = new Boolean[7];

		Arrays.fill(selected, false);
		final int QUIT = 6;
		int command = 0;

		System.out.println("[안전 편의 시설]");
		System.out.println("1. 일산화탄소 경보기");
		System.out.println("2. 화재 경보기");
		System.out.println("3. 소화기");
		System.out.println("4. 구급상자");
		System.out.println("5. 비상 대피 안내도 및 현지 응급 구조기관 번호");
		System.out.println("6. 입력 종료");
		System.out.println("------------------------");

		System.out.print("입력 (ex. 1 2 6) : ");
		while (command != QUIT) {
			command = SCANNER.nextInt();
			selected[command] = true;
		}

		SCANNER.nextLine();
		return selected;
	}

	public Boolean[] registAccessibilityAmenities() {
		Boolean[] selected = new Boolean[6];

		Arrays.fill(selected, false);
		final int QUIT = 5;
		int command = 0;

		System.out.println("[접근성 편의 시설]");
		System.out.println("1. 계단이나 단차가 없는 현관");
		System.out.println("2. 폭 32인치/81cm 이상의 넓은 출입구");
		System.out.println("3. 폭 36인치/91cm 이상의 넓은 복도");
		System.out.println("4. 휠체어 접근 가능 욕실");
		System.out.println("5. 입력 종료");

		System.out.println("------------------------");
		System.out.print("입력 (ex. 1 2 5) : ");
		while (command != QUIT) {
			command = SCANNER.nextInt();
			selected[command] = true;
		}
		SCANNER.nextLine();


		return selected;
	}
}

