package Controller;

import Container.IocContainer;
import lombok.AllArgsConstructor;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccomRecognizeInfo;
import network.Protocol.Packet.AccommodationRegister;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.AmenityDTO;
import persistence.dto.UserDTO;
import service.AccommodationService;
import service.UserService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserController implements MethodController {
	private UserService userService;
	private AccommodationService acService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	public UserController(IocContainer iocContainer) {
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
			response.setMessage("ID or PW fail");
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
				res = selectAccomByUser(req);
			}
			case GUEST -> {
			}
		}
		return res;
	}

	private Response selectAccomByUser(Request req) {
		UserDTO userDTO = (UserDTO) req.getPayload();
		List<AccommodationDTO> myAccomList = acService.selectConfirmedAccomByUser(userDTO);
		Response response = Response.builder().isSuccess(true).payload(myAccomList).build();
		return response;
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
				res = registAccom(req);
			}
			case GUEST -> {
			}

		}

		return res;
	}

	//1.숙소등록
	private Response registAccom(Request req) {
		AccommodationRegister accommodationRegister = (AccommodationRegister) req.getPayload();
		AccommodationDTO accommodationDTO = accommodationRegister.getAccommodationDTO();
		List<Boolean[]> amenityList = accommodationRegister.getAmenityList();

		Boolean[] basicAmenities = amenityList.get(0);
		Boolean[] accessibilityAmenities = amenityList.get(1);
		Boolean[] safetyAmenites = amenityList.get(2);
		Boolean[] favoriteAmenites = amenityList.get(3);

		List<AmenityDTO> basicAmenitieList = acService.selectAmenityByCategory("기본"); //1~6
		List<AmenityDTO> safetyAmeniteList = acService.selectAmenityByCategory("안전"); //7~11
		List<AmenityDTO> accessAmenitieList = acService.selectAmenityByCategory("접근성"); //12~15
		List<AmenityDTO> favAmenitieList = acService.selectAmenityByCategory("선호"); //

		acService.insertAccom(accommodationDTO);

		for (int i = 0; i < basicAmenitieList.size(); i++) {
			AmenityDTO amenityDTO = basicAmenitieList.get(i);
			if (basicAmenities[i + 1]) {
				acService.insertAccomAmenity(accommodationDTO, amenityDTO);
			}
		}
		for (int i = 0; i < safetyAmeniteList.size(); i++) {
			AmenityDTO amenityDTO = safetyAmeniteList.get(i);
			if (safetyAmenites[i + 1]) {
				acService.insertAccomAmenity(accommodationDTO, amenityDTO);
			}
		}
		for (int i = 0; i < accessAmenitieList.size(); i++) {
			AmenityDTO amenityDTO = accessAmenitieList.get(i);
			if (accessibilityAmenities[i + 1]) {
				acService.insertAccomAmenity(accommodationDTO, amenityDTO);
			}
		}
		for (int i = 0; i < favAmenitieList.size(); i++) {
			AmenityDTO amenityDTO = favAmenitieList.get(i);
			if (favoriteAmenites[i + 1]) {
				acService.insertAccomAmenity(accommodationDTO, amenityDTO);
			}
		}

		Response response = new Response();
		response.setIsSuccess(true);
		return response;
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