package org.airbnb;

import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import view.UserView;

import java.sql.SQLException;
import java.util.List;

public class Main {
	public static void main(String[] args) throws SQLException {
		UserDAO myUserDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		List<UserDTO> userDTOList = myUserDAO.selectAll();

		List<UserDTO> userDTOS;
		UserView userViewer = new UserView();
		userViewer.printAll(userDTOList);
	}
}