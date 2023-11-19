package view;

import persistence.dto.UserDTO;

public class Viewer {
	public void welcome() {
		System.out.println("=======숙박 예약 시스템=======");
		System.out.println("1.게스트로 로그인");
		System.out.println("2.호스트로 로그인");
		System.out.println("3.관리자로 로그인");
		System.out.println("4.계정 생성");
		System.out.println("===========================");
	}

	public void adminView(UserDTO user) {
		System.out.println("============관리자============");
		System.out.println("1. 승인 요청 목록 보기");
		System.out.println("2. 숙소 현황");
		System.out.println("=============================");
	}

	public void guestView(UserDTO user) {

	}

	public void hostView(UserDTO user) {
		System.out.println("============호스트============");
		System.out.println("1. 숙소 등록 신청");
		System.out.println("2. 요금 정책 설정");
		System.out.println("3. 숙박 예약 현황 보기");
		System.out.println("4. 숙박 예약 승인/거절");
		System.out.println("5. 리뷰 답글 등록");
		System.out.println("=============================");
	}
}
