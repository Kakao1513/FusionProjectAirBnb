package Controller;

import network.Protocol.Enums.JobType;
import network.Protocol.Enums.Method;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import service.AccommodationService;
import service.UserService;
import view.AccommodationView;
import view.UserView;

import java.util.Optional;
import java.util.Scanner;

public class UserController implements Controller {
	private static Scanner sc;
	private UserView userView;
	private UserService userService;
	private AccommodationService acService;
	private AccommodationView acView;
	private UserDTO currentUser;

	public UserController(UserService userService, UserView userView) {
		this.userService = userService;
		this.userView = userView;
	}

	public UserController(UserService userService, UserView userView, AccommodationService accommodationService, AccommodationView acView) {
		this.userService = userService;
		this.userView = userView;
		this.acService = accommodationService;
		this.acView = acView;
	}

	public UserController(UserService userService) {
		this.userService = userService;
	}


	public UserDTO login(UserDTO loginInfo) {
		String id = loginInfo.getAccountId();
		String pw = loginInfo.getPassword();
		Optional<UserDTO> userDTO = userService.loginUser(id, pw); //추후에 service 객체를 네트워크를 통해서 전달하여 serverDB에서 정보를 가져옴.
		return userDTO.orElse(null);
	}

	public Response loginProcess(Request req) {
		UserDTO userDTO = (UserDTO) req.getPayload();
		Response response = new Response();

		UserDTO sendDTO = login(userDTO);
		if (sendDTO == null) {
			response.setErrorMessage("ID or PW fail");
			response.setIsSuccess(false);
			response.setPayload(null);
		} else {
			response.setIsSuccess(true);
			response.setPayload(sendDTO);
		}
		return response;
	}

	@Override
	public Response handle(Request req) {
		Method method = req.getMethod();
		JobType count = req.getJobType();
		switch (method) {
			case GET -> {
				return null; //TODO:구현 요
			}
			case PUT -> {
				return null; //TODO:구현 요
			}
			case POST -> {
				if (count == JobType.COMMON) {
					return loginProcess(req);
				} else {
					return null; //TODO:다른 프로세스가 들어감
				}
			}
			case DELETE -> {
				return null; //TODO:구현 요
			}
			default -> {
				return null;
			}
		}
	}
}