package Controller;

import persistence.dto.UserDTO;
import service.UserService;
import view.UserView;

import java.io.InputStream;
import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;


public class UserController {
	private static Scanner sc;
	private UserView view;
	private UserService service;
	private UserDTO currentUser;

	public UserController(UserService userService, UserView view) {
		service = userService;
		this.view = view;
	}

	public void setInput(InputStream in) {
		sc = new Scanner(in);
	}

	public void jobOption() {
		view.viewJobs(currentUser);
		int select = Integer.parseInt(sc.nextLine());
		switch (select) {
			case 1 -> {
				guestJob();
			}
			case 2 -> {

			}
			case 3 -> {

			}
			case 4 -> {

			}
			default -> {

			}
		}
	}

	public void login() {
		System.out.println("==========로그인===========");
		System.out.print("ID : ");
		String id = sc.nextLine();
		System.out.print("PW : ");
		String pw = sc.nextLine();
		Optional<UserDTO> userDTO = service.loginUser(id, pw);
		userDTO.ifPresent(userDTO1 -> currentUser = userDTO1);
	}

	public void guestJob(){
		view.viewGuestJob();
		int option = Integer.parseInt(sc.nextLine());
		switch (option){
			case 1->{
				view.viewMyPage(currentUser);
				myPageJob();
			}
			case 2->{

			}
			case 3->{

			}
			case 4->{

			}
		}
	}
	public void myPageJob(){
		int option = Integer.parseInt(sc.nextLine());
		switch (option){
			case 1->{

			}
			case 2->{

			}
			case 3->{

			}
			case 4->{
				updateUser();
			}
		}
	}
	public void updateUser() {
		System.out.println("이름 입력 (원치 않으면 x) : ");
		String name = sc.nextLine();
		System.out.println("전화 번호 입력 (원치 않으면 x) : ");
		String phone = sc.nextLine();
		System.out.println("생일 입력 (원치 않으면 x):");
		String birthString = sc.nextLine();
		Date birth = null;
		if (!birthString.equalsIgnoreCase("x")) {
			birth = Date.valueOf(birthString);
		}
		service.changePrivacy(currentUser, name, birth, phone);
	}
}
