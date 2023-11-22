package view;

import persistence.dto.UserDTO;

import java.util.Scanner;

public class UserView extends View<UserDTO> {
	Scanner sc = new Scanner(System.in);

	public UserDTO loginRequestView() {
		System.out.println("================로그인===================");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PW : ");
		String pw = sc.nextLine();
		return new UserDTO(id, pw);
	}

	public int viewJobs(UserDTO dto) {
		System.out.println();
		String userType = dto.getType();
		if (userType.equalsIgnoreCase("ADMIN")) {
			System.out.printf("===============관리자%s님 환영합니다.==============\n", dto.getName());
		} else {
			System.out.printf("===============%s님 환영합니다.==============\n", dto.getName());
		}
		System.out.println("================업무를 선택하세요==============");
		System.out.println("(0) 로그 아웃");
		System.out.println("(1) 게스트");
		System.out.println("(2) 호스트");
		if (userType.equalsIgnoreCase("ADMIN")) {
			System.out.println("(3) 관리자");
		}
		System.out.println("============================================");
		int select;
		int upperLimit = userType.equalsIgnoreCase("ADMIN") ? 3 : 2;
		while (true) {
			System.out.print("Input : ");
			select = Integer.parseInt(sc.nextLine());
			if (0 <= select && select <= upperLimit) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}

		return select;
	}

	public int selectGuestJob() {
		System.out.println();
		System.out.println("=================GUEST==================");
		System.out.println("(0) 이전 페이지로");
		System.out.println("(1) MyPage");
		System.out.println("(2) 숙소 목록 보기");
		System.out.println("(3) 숙소 필터링");
		System.out.println("(4) 숙소 예약 신청");
		System.out.println("========================================");
		int select = 1;
		while (select != 0) {
			System.out.print("Input : ");
			select = Integer.parseInt(sc.nextLine());
			if (0 <= select && select <= 4) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
		return select;
	}



	public int selectMyPageJobs(UserDTO dto) {
		System.out.println();
		System.out.printf("===============%s님의 MyPage==============\n", dto.getName());
		System.out.println("이름 : " + dto.getName());
		System.out.println("전화번호 : " + dto.getPhone());
		System.out.println("생년 월일 : " + dto.getBirth());
		System.out.println("===============업무를 선택하세요==============");
		System.out.println("(0) 이전 페이지로");
		System.out.println("(1) 숙소 예약 현황 조회");
		System.out.println("(2) 예약 취소");
		System.out.println("(3) 리뷰와 별점 등록");
		System.out.println("(4) 개인 정보 수정");
		System.out.println("====================================");
		System.out.print("Input : ");
		int select = 1;
		while(select!=0){
			select = Integer.parseInt(sc.nextLine());
			if (0 <= select && select <= 4) {
				break;
			} else {
				System.out.println("잘못된 입력입니다.");
			}
		}
		return select;
	}


}
