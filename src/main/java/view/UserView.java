package view;

import persistence.dto.UserDTO;

public class UserView extends View<UserDTO> {

	public void loginView() { //수정 필요
		System.out.println("=============로그인=============");
		System.out.println("ID :");
		System.out.println("PW :");
	}

	public void viewMyPage(UserDTO dto) {
		System.out.printf("===============%s님의 MyPage==============", dto.getName());
		System.out.println("이름 : " + dto.getName());
		System.out.println("전화번호 : " + dto.getPhone());
		System.out.println("생년 월일 : " + dto.getBirth());
		System.out.println("=============업무를 선택해주세요 =============");
		System.out.println("1 ) 개인정보 수정");
		System.out.println("2 ) 예약 현황 조회");
		System.out.println("3 ) 예약 취소");
		System.out.println("4 ) 리뷰와 별점 등록");
	}

}
