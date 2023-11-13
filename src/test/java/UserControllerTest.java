import Controller.UserController;
import org.junit.jupiter.api.Test;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import service.UserService;
import view.UserView;

public class UserControllerTest {
	UserController userController;
	UserView view;
	UserService userService;
	UserDAO userDAO;

	@Test
	void updateUser() {
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		userService = new UserService(userDAO);
		view = new UserView();
		userController = new UserController(userService, view);
		userController.setInput(System.in);
		userController.login();
		userController.jobOption();
	}
}
