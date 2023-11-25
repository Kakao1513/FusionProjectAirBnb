package Controller;

import Container.IocContainer;
import lombok.AllArgsConstructor;
import network.Protocol.Enums.RoleType;
import network.Protocol.Enums.Method;
import network.Protocol.Packet.AccomRecognizeInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import service.AccommodationService;
import service.ReservationService;
import service.UserService;

import java.sql.Date;
import java.util.Optional;

@AllArgsConstructor
public class UserController implements MethodController {
	private UserService userService;
	private AccommodationService acService;

	public UserController(UserService userService) {
		this.userService = userService;
	}
	public UserController(IocContainer iocContainer){
		this.userService = iocContainer.userService();
		this.acService = iocContainer.accommodationService();
	}

	private UserDTO login(UserDTO loginInfo) {
		String id = loginInfo.getAccountId();
		String pw = loginInfo.getPassword();
		Optional<UserDTO> userDTO = userService.loginUser(id, pw); //추후에 service 객체를 네트워크를 통해서 전달하여 serverDB에서 정보를 가져옴.
		return userDTO.orElse(null);
	}

	private Response loginProcess(Request req) {
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

	private Response changeInfo(Request req) {
		Object[] body = (Object[]) req.getPayload();
		UserDTO chUser = userService.changePrivacy((UserDTO) body[0], (String) body[1], (Date) body[2], (String) body[3]);
		Response res = new Response();
		res.setPayload(chUser);
		res.setIsSuccess(true);
		res.setMethod(req.getMethod());
		res.setRoleType(req.getRoleType());
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

	@Override
	public Response getHandle(Request req) {
		RoleType roleType = req.getRoleType();
		Response res = null;
		switch (roleType) {
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
	@Override
	public Response putHandle(Request req) {
		RoleType roleType = req.getRoleType();
		Response res = null;
		switch (roleType) {
			case COMMON -> {
			}
			case ADMIN -> {
				res = changeAccomStatus(req);
			}
			case HOST -> {
			}
			case GUEST -> {
				res = changeInfo(req);
			}
		}

		return res;
	}

	private Response changeAccomStatus(Request req) {
		AccomRecognizeInfo recognizeInfo = (AccomRecognizeInfo) req.getPayload();
		acService.updateAccomStatus(recognizeInfo.getAccomID(), recognizeInfo.getStatus().getName());
		Response response = new Response();
		response.setIsSuccess(true);
		return response;
	}

	@Override
	public Response postHandle(Request req) {
		RoleType roleType = req.getRoleType();
		Response res = null;
		switch (roleType) {
			case COMMON -> {
				res = loginProcess(req);
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

	@Override
	public Response deleteHandle(Request req) {
		RoleType roleType = req.getRoleType();
		Response res = null;
		switch (roleType) {
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