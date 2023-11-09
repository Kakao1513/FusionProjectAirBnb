package service;

import persistence.dao.UserDAO;
import persistence.dto.UserDTO;

import java.util.Optional;

public class UserService {
	private final UserDAO userDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	private boolean login(String id, String pw) {
		if (pw.equals(userDAO.getPassword(id))) {
			System.out.println("로그인 성공!");
			return true;
		} else {
			System.out.println("로그인 실패!");
			return false;
		}
	}


	public Optional<UserDTO> getRecognizedUser(String id, String pw) {
		if (login(id, pw)) {
			return Optional.of(userDAO.getUser(id));
		} else {
			return Optional.empty();
		}
	}
}
