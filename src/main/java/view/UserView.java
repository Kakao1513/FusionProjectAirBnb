package view;

import persistence.dto.UserDTO;

public class UserView extends View<UserDTO> {

	public UserDTO loginRequestView() {
		System.out.println("================로그인===================");
		System.out.print("ID : ");
		String id = SCANNER.nextLine();
		System.out.print("PW : ");
		String pw = SCANNER.nextLine();
		return new UserDTO(id, pw);
	}

	public int viewJobs(UserDTO dto) {
		System.out.println();
		String userType = dto.getType();
		if (userType.equalsIgnoreCase("ADMIN")) {
			System.out.printf("===============관리자 %s님 환영합니다.==============\n", dto.getName());
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
		int upperLimit = userType.equalsIgnoreCase("ADMIN") ? 3 : 2;
		return rangeSelect(0, upperLimit);
	}

	public int selectGuestJob() {
		System.out.println();
		System.out.println("=================GUEST==================");
		System.out.println("(0) 이전 페이지로");
		System.out.println("(1) MyPage");
		System.out.println("(2) 숙소 목록 보기");
		System.out.println("(3) 숙소 예약 신청");
		System.out.println("========================================");
		return rangeSelect(0, 3);
	}

	public int selectHostJob() {
		System.out.println();
		System.out.println("=================HOST==================");
		System.out.println("(0) 이전 페이지로");
		System.out.println("(1) 숙소 등록 신청");
		System.out.println("(2) 요금 정책 설정");
		System.out.println("(3) 할인 정책 설정");
		System.out.println("(4) 숙박 예약 현황 보기");
		System.out.println("(5) 게스트의 숙박 예약 승인/거절");
		System.out.println("(6) 게스트 리뷰에 대한 답글 등록");
		System.out.println("(7) 내 숙소 목록 보기");
		System.out.println("=======================================");
		return rangeSelect(0, 7);
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
		return rangeSelect(0, 4);
	}

	public int selectAdminJob() {
		System.out.println("===============업무를 선택하세요=============");
		System.out.println("(0) 이전 페이지로");
		System.out.println("(1) 숙소 등록 승인/거절");
		System.out.println("(2) 숙소별 월별 예약 현황 확인");
		System.out.println("(3) 숙소별 월별 총매출 확인");
		System.out.println("==========================================");
		return rangeSelect(0, 3);
	}


}
