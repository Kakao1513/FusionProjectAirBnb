package Controller;

import lombok.AllArgsConstructor;
import network.Protocol.Enums.JobType;
import network.Protocol.Enums.Method;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import service.AccommodationService;
import service.UserService;
import view.AccommodationView;
import view.UserView;

import java.sql.Date;
import java.util.Optional;
import java.util.Scanner;

@AllArgsConstructor
public class UserController implements Controller {
	private UserService userService;
	private AccommodationService acService;

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

	public Response changeInfo(Request req) {
		Object[] body = (Object[]) req.getPayload();
		UserDTO chUser = userService.changePrivacy((UserDTO) body[0], (String) body[1], (Date) body[2], (String) body[3]);
		Response res = new Response();
		res.setPayload(chUser);
		res.setIsSuccess(true);
		res.setMethod(req.getMethod());
		res.setJobType(req.getJobType());
		res.setPayloadType(req.getPayloadType());
		return res;
	}


	@Override
	public Response handle(Request req) {
		Method method = req.getMethod();
		Response res = null;
		switch (method) {
			case GET -> {
				res = getHandle(req);
			}
			case PUT -> {
				res = putHandle(req);
			}
			case POST -> {
				res = postHandle(req);
			}
			case DELETE -> {
				res = deleteHandle(req);
			}
		}
		return res;
	}

	public Response getHandle(Request req) {
		JobType jobType = req.getJobType();
		Response res = null;
		switch (jobType) {
			case COMMON -> {
			}
			case ADMIN -> {
			}
			case HOST -> {
			}
			case GUEST -> {
			}
		}
		return res;
	}

	public Response putHandle(Request req) {
		JobType jobType = req.getJobType();
		Response res = null;
		switch (jobType) {
			case COMMON -> {
			}
			case ADMIN -> {
			}
			case HOST -> {
			}
			case GUEST -> {
				res = changeInfo(req);
			}
		}

		return res;
	}

	public Response postHandle(Request req) {
		JobType jobType = req.getJobType();
		Response res = null;
		switch (jobType) {
			case HOST -> {
			}
			case ADMIN -> {
			}
			case GUEST -> {
			}
			case COMMON -> {
				res = loginProcess(req);
			}
		}

		return res;
	}

	public Response deleteHandle(Request req) {
		JobType jobType = req.getJobType();
		Response res = null;
		switch (jobType) {
			case COMMON -> {
			}
			case ADMIN -> {
			}
			case HOST -> {
			}
			case GUEST -> {
			}
		}

		return res;
	}
}