package Controller;

import Container.IocContainer;
import lombok.AllArgsConstructor;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dao.*;
import service.AccommodationService;
import service.UserService;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class MainController implements Controller {
	private UserController userController;
	private AccommodationController accommodationController;
	private ReservationController reservationController;
	private Map<PayloadType, Controller> controllerMap;

	public MainController(UserDAO userDAO, AccommodationDAO accomDAO, AmenityDAO amenityDAO,
	                      ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, ReviewDAO reviewDAO) {
		AccommodationService acService = new AccommodationService(accomDAO, amenityDAO, reservationDAO, ratePolicyDAO, reviewDAO);
		UserService userService = new UserService(userDAO);
		userController = new UserController(userService);
		accommodationController = new AccommodationController(acService);
		reservationController = new ReservationController(userService, acService);

		controllerMap = new HashMap<>();
		controllerMap.put(PayloadType.USER, userController);
		controllerMap.put(PayloadType.ACCOMMODATION, accommodationController);
	}

	public MainController(IocContainer iocContainer) {


		UserService userService = new UserService(iocContainer.userDAO());
		AccommodationService acService = new AccommodationService(iocContainer);
		userController = new UserController(userService);
		accommodationController = new AccommodationController(acService);
		reservationController = new ReservationController(userService, acService);
		controllerMap = new HashMap<>();
		controllerMap.put(PayloadType.USER, userController);
		controllerMap.put(PayloadType.ACCOMMODATION, accommodationController);
		controllerMap.put(PayloadType.RESERVATION, reservationController);
		//TODO: 컨트롤러 추가시 더 추가됨
	}

	@Override
	public Response handle(Request request) {
		PayloadType firstType = request.getPayloadType();
		Controller controller = controllerMap.get(firstType);
		Response rs = null;
		if (controller != null) {
			rs = controller.handle(request);
		}
		return rs;
	}

}
