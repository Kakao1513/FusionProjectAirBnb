import Controller.UserController;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import service.UserService;

import java.io.ByteArrayInputStream;

public class UserControllerTest {
	UserController userController;
	UserService userService;
	UserDAO userDAO;
	@Test
	void updateUser(){
		String input = "minj21\ntest1\n성민제\n010-5432-5232\n2020-02-21";

		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		userService = new UserService(userDAO);
		userController = new UserController(userService);
		userController.setInput(new ByteArrayInputStream(input.getBytes()));
		userController.login();
		userController.updateUser();
	}
}
