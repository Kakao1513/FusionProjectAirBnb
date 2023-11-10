package Controller;

import persistence.dto.UserDTO;
import service.UserService;

import java.io.InputStream;
import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;


public class UserController {
	private UserService service;
	private UserDTO currentUser;
	private static Scanner sc;

	public void setInput(InputStream in){
		System.setIn(in);
		sc = new Scanner(System.in);
	}

	public UserController(UserService userService) {
		service = userService;
	}

	public void login() {
		String id = sc.nextLine();
		String pw = sc.nextLine();
		Optional<UserDTO> userDTO = service.getRecognizedUser(id, pw);
		userDTO.ifPresent(userDTO1 -> currentUser = userDTO1);
	}

	public void updateUser() {
		String name = sc.nextLine();
		String phone = sc.nextLine();
		Date birth = Date.valueOf(sc.nextLine());
		service.changePrivacy(currentUser, name, birth, phone);

	}
}
