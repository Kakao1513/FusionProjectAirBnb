package network.Server;

import Container.IocContainer;
import persistence.MyBatisConnectionFactory;
import persistence.dao.*;

public class ServerRunner {
	public static void main(String[] args) {/*
		UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		ReviewDAO reviewDAO = new ReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		AccommodationDAO accommodationDAO = new AccommodationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		RatePolicyDAO ratePolicyDAO = new RatePolicyDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		ReservationDAO reservationDAO = new ReservationDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		AmenityDAO amenityDAO = new AmenityDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		IocContainer iocContainer =new IocContainer();*/
		Server server = new Server(IocContainer.iocContainer());
		server.run();
	}
}
