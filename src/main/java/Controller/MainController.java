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
	private ReviewController reviewController;
	private Map<PayloadType, Controller> controllerMap;


	public MainController(IocContainer iocContainer) {
		userController = new UserController(iocContainer);
		accommodationController = new AccommodationController(iocContainer);
		reservationController = new ReservationController(iocContainer);
		reviewController = new ReviewController(iocContainer);
		controllerMap = new HashMap<>();
		controllerMap.put(PayloadType.USER, userController);
		controllerMap.put(PayloadType.ACCOMMODATION, accommodationController);
		controllerMap.put(PayloadType.RESERVATION, reservationController);
		controllerMap.put(PayloadType.REVIEW, reviewController);
		//컨트롤러 추가시 더 추가됨
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
