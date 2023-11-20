package network.Server;

import Controller.UserController;
import persistence.MyBatisConnectionFactory;
import persistence.dao.*;
import service.UserService;

public class ServerRunner {
	public static void main(String[] args){
		UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		ReviewDAO reviewDAO =new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		AccommodationDAO accommodationDAO =new AccommodationDAO(MyBatisConnectionFactory.getSqlSessionFactory()) ;
		RatePolicyDAO ratePolicyDAO = new RatePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		AmenityDAO amenityDAO =new AmenityDAO(MyBatisConnectionFactory.getSqlSessionFactory());

		Server server = new Server(accommodationDAO,userDAO,reviewDAO,reservationDAO,ratePolicyDAO,amenityDAO);
		server.run();
	}
}
