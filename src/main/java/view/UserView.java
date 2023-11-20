package view;

import persistence.dto.UserDTO;

import java.util.Scanner;

public class UserView extends View<UserDTO> {

	public UserDTO loginRequestView(){
		Scanner sc = new Scanner(System.in);
		System.out.println("================로그인===================");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PW : ");
		String pw = sc.nextLine();
		return new UserDTO(id,pw);
	}

	public void viewJobs(UserDTO dto){
		System.out.println();
		if (dto.getType().equalsIgnoreCase("ADMIN")) {
			System.out.printf("===============관리자%s님 환영합니다.==============\n", dto.getName());
		} else {
			System.out.printf("===============%s님 환영합니다.==============\n", dto.getName());
		}
		System.out.println("================업무를 선택하세요==============");
		System.out.println("(1) 게스트");
		System.out.println("(2) 호스트");
		if(dto.getType().equalsIgnoreCase("ADMIN")){
			System.out.println("(3) 관리자");
		}
		System.out.println("============================================");
		System.out.print("Input : ");
	}

	public void viewGuestJob(){
		System.out.println();
		System.out.println("=================GUEST==================");
		System.out.println("(1) MyPage");
		System.out.println("(2) 숙소 목록 보기");
		System.out.println("(3) 숙소 필터링");
		System.out.println("(4) 숙소 예약 신청");
		System.out.println("========================================");
		System.out.print("Input : ");

	}

	public void viewMyPage(UserDTO dto) {
		System.out.println();
		System.out.printf("===============%s님의 MyPage==============\n", dto.getName());
		System.out.println("이름 : " + dto.getName());
		System.out.println("전화번호 : " + dto.getPhone());
		System.out.println("생년 월일 : " + dto.getBirth());
		System.out.println("=========================================");
		viewMyPageJobs();
	}

	public void viewMyPageJobs(){
		System.out.println("===============MyPage==============");
		System.out.println("(1) 숙소 예약 현황 조회");
		System.out.println("(2) 예약 취소");
		System.out.println("(3) 리뷰와 별점 등록");
		System.out.println("(4) 개인 정보 수정");
		System.out.println("====================================");
		System.out.print("Input : ");
	}


}
