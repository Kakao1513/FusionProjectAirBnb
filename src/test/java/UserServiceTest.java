import Container.IocContainer;
import org.junit.jupiter.api.Test;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import service.UserService;
import view.UserView;

import java.util.Optional;
import java.util.Scanner;

public class UserServiceTest {
	private UserService userService;
	private UserView userView;

	@Test
	public void loginSuccess() {
		userService = new UserService(IocContainer.iocContainer());
		userView = new UserView();
		String id = "minj21", pw = "mjmj0221";
		System.out.print("ID : "); //View로 변경

		System.out.print("PW : ");
		Optional<UserDTO> userDTO = userService.loginUser(id, pw);
		userDTO.ifPresent(dto -> userView.print(dto));
	}

	@Test
	public void loginFail(){
		userService = new UserService(IocContainer.iocContainer());
		userView = new UserView();
		String id = "bell", pw = "fail";
		Optional<UserDTO> userDTO = userService.loginUser(id, pw);
		userDTO.ifPresent(dto -> userView.print(dto));
	}
}