package service;

import lombok.Builder;
import persistence.dao.AccommodationDAO;
import persistence.dao.ReservationDAO;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;

import java.sql.Date;
import java.util.Optional;

@Builder
public class UserService {
	private UserDAO userDAO;
	private AccommodationDAO acDAO;
	private ReservationDAO rDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public UserService(UserDAO userDAO, AccommodationDAO accommodationDAO, ReservationDAO rDAO) {
		this.userDAO = userDAO;
		acDAO = accommodationDAO;
		this.rDAO = rDAO;
	}
	private boolean login(String id, String pw) {
		return pw.equals(userDAO.selectPwById(id));
	}

	public Optional<UserDTO> loginUser(String id, String pw) {
		if (login(id, pw)) {
			return Optional.of(userDAO.selectById(id));
		} else {
			return Optional.empty();
		}
	}

	public void changePrivacy(UserDTO userDTO, String name, Date birth, String phone) {
		String newName, newPhone;
		Date newBirth;
		if (name.equalsIgnoreCase("x")) {
			newName = userDTO.getName();
		} else {
			newName = name;
		}
		if (birth == null) {
			newBirth = userDTO.getBirth();
		} else {
			newBirth = birth;
		}
		if (phone.equalsIgnoreCase("x")) {
			newPhone = userDTO.getPhone();
		} else {
			newPhone = phone;
		}
		userDTO.setName(newName);
		userDTO.setPhone(newPhone);
		userDTO.setBirth(newBirth);
		userDAO.updateUser(userDTO);
	}

	public void reserveRequest(){

	}

}
