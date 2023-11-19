package network.Server;

import Controller.UserController;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import service.UserService;

public class ServerRunner {
	public static void main(String[] args){
		UserDAO userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		UserService userService = new UserService(userDAO);
		UserController userController = new UserController(userService);
		Server server = new Server(userController);
		server.run();
	}
}
