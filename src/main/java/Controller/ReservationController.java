package Controller;

import Container.IocContainer;
import lombok.AllArgsConstructor;
import network.Protocol.Enums.RoleType;
import network.Protocol.Enums.Method;
import network.Protocol.Request;
import network.Protocol.Response;
import service.AccommodationService;
import service.ReservationService;
import service.UserService;

@AllArgsConstructor
public class ReservationController implements MethodController {
	private final UserService userService;
	private final AccommodationService accommodationService;
	private final ReservationService reservationService;

	public ReservationController(IocContainer iocContainer){
		this.userService = iocContainer.userService();
		this.accommodationService = iocContainer.accommodationService();
		this.reservationService = iocContainer.reservationService();
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

	public Response putHandle(Request req) {
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

	public Response postHandle(Request req) {
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
