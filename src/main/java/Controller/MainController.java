package Controller;

import network.Protocol.Enums.PayloadType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dao.*;
import service.AccommodationService;
import service.UserService;

public class MainController implements Controller {
	UserController userController;
	AccommodationController accommodationController;

	public MainController(UserDAO userDAO, AccommodationDAO accomDAO, AmenityDAO amenityDAO,
	                      ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, ReviewDAO reviewDAO) {
		AccommodationService acService = new AccommodationService(accomDAO, amenityDAO, reservationDAO, ratePolicyDAO, reviewDAO);
		UserService userService = new UserService(userDAO);
		userController = new UserController(userService);
		accommodationController = new AccommodationController(acService);
	}

	@Override
	public Response handle(Request request) {
		PayloadType firstType = request.getPayloadType();
		switch (firstType) {
			case USER -> {
				return userController.handle(request);
			}
			case REVIEW -> {
				return null;
			}
			case RESERVATION -> {
				return null;
			}
			case ACCOMMODATION -> {
				return null;
			}
			default -> {
				return null;
			}
		}
	}
}
