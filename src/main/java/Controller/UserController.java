package Controller;

import network.Protocol.UserLoginInfo;
import persistence.dto.AccommodationDTO;
import persistence.dto.UserDTO;
import service.AccommodationService;
import service.UserService;
import view.AccommodationView;
import view.UserView;

import java.io.InputStream;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserController {
	private static Scanner sc;
	private UserView userView;
	private UserService userService;
	private AccommodationService acService;
	private AccommodationView acView;
	private UserDTO currentUser;

	public UserController(UserService userService, UserView userView) {
		this.userService = userService;
		this.userView = userView;
	}
	public UserController(UserService userService, UserView userView, AccommodationService accommodationService, AccommodationView acView) {
		this.userService = userService;
		this.userView = userView;
		this.acService = accommodationService;
		this.acView = acView;
	}

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public void setInput(InputStream in) {
		sc = new Scanner(in);
	}

	public void jobOption() {
		while (true) {
			userView.viewJobs(currentUser);
			int select = Integer.parseInt(sc.nextLine());
			switch (select) {
				case 0 -> {
					return;
				}
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
	}

	public UserDTO login(UserLoginInfo loginInfo) {
		String id = loginInfo.getId();
		String pw = loginInfo.getPw();
		Optional<UserDTO> userDTO = userService.loginUser(id, pw); //추후에 service 객체를 네트워크를 통해서 전달하여 serverDB에서 정보를 가져옴.
		return userDTO.orElse(null);
	}

	public void guestJob() {
		while (true) {
			userView.viewGuestJob();
			int option = Integer.parseInt(sc.nextLine());
			switch (option) {
				case 0 -> {
					return;
				}
				case 1 -> {
					userView.viewMyPage(currentUser);
					myPageJob();
				}
				case 2 -> {
					List<AccommodationDTO> acDTOs = acService.selectAccom("승인됨");
					acView.displayAccomList(acDTOs);
				}
				case 3 -> {

				}
				case 4 -> {

				}
			}
		}
	}

	public void myPageJob() {
		while (true) {
			int option = Integer.parseInt(sc.nextLine());
			switch (option) {
				case 0 -> {
					return;
				}
				case 1 -> {

				}
				case 2 -> {

				}
				case 3 -> {

				}
				case 4 -> {
					updateUser();
				}
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
		userService.changePrivacy(currentUser, name, birth, phone);
	}
}
