package org.airbnb;

import Controller.UserController;
import persistence.MyBatisConnectionFactory;
import persistence.dao.AccommodationDAO;
import persistence.dao.UserDAO;
import service.AccommodationService;
import service.UserService;
import view.AccommodationView;
import view.ReservationView;
import view.ReviewView;
import view.UserView;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) throws SQLException {
		LocalDate localDate = LocalDate.of(2023, 11, 30);
		LocalDate localDate1 = LocalDate.of(2023, 12, 5);

		System.out.println(localDate1.getDayOfYear());
	}
}
