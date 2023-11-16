package org.airbnb;

import Controller.UserController;
import persistence.MyBatisConnectionFactory;
import persistence.dao.AccommodationDAO;
import persistence.dao.UserDAO;
import service.AccommodationService;
import service.UserService;
import view.AccommodationView;
import view.UserView;

import java.sql.SQLException;

public class Main {

	public static void main(String[] args) throws SQLException {
		UserController userController;
		UserView view;
		AccommodationView acView = new AccommodationView();
		UserService userService;
		UserDAO userDAO;
		AccommodationDAO acDAO= new AccommodationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		AccommodationService acService = new AccommodationService(acDAO);
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		userService = new UserService(userDAO);
		view = new UserView();
		userController = new UserController(userService, view, acService, acView);
		userController.setInput(System.in);
		userController.login();
		userController.jobOption();

	}
}
