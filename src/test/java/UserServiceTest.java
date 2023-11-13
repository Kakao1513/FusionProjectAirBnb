import org.junit.jupiter.api.Test;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import service.UserService;
import view.UserView;

import java.util.Optional;
import java.util.Scanner;

public class UserServiceTest {
	private Scanner sc;
	private UserService userService;
	private UserDAO userDAO;
	private UserView userView;

	@Test
	public void loginSuccess() {
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		userService = new UserService(userDAO);
		userView = new UserView();
		String id = "minj21", pw = "mjmj0221";
		System.out.print("ID : "); //View로 변경

		System.out.print("PW : ");
		Optional<UserDTO> userDTO = userService.loginUser(id, pw);
		userDTO.ifPresent(dto -> userView.print(dto));
	}

	@Test
	public void loginFail(){
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		userService = new UserService(userDAO);
		userView = new UserView();
		String id = "bell", pw = "fail";
		Optional<UserDTO> userDTO = userService.loginUser(id, pw);
		userDTO.ifPresent(dto -> userView.print(dto));
	}
}