package service;

import persistence.dao.UserDAO;
import persistence.dto.UserDTO;

import java.sql.Date;
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

	public void changePrivacy(UserDTO userDTO, String name, Date birth, String phone) {
		String newName, newPhone;
		Date newBirth;
		if (name == null) {
			newName = userDTO.getName();
		} else {
			newName = name;
		}
		if (birth == null) {
			newBirth = userDTO.getBirth();
		} else {
			newBirth = birth;
		}
		if (phone == null) {
			newPhone = userDTO.getPhone();
		} else {
			newPhone = phone;
		}
		userDTO.setName(newName);
		userDTO.setPhone(newPhone);
		userDTO.setBirth(newBirth);
		userDAO.updateUser(userDTO);
	}
}
