package Controller;

import Container.IocContainer;
import lombok.AllArgsConstructor;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.RoleType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.UserDTO;
import service.AccommodationService;
import service.ReservationService;
import service.UserService;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

@AllArgsConstructor
public class ReservationController implements MethodController {
	private final UserService userService;
	private final AccommodationService accommodationService;
	private final ReservationService reservationService;

	public ReservationController(IocContainer iocContainer) {
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
				res = getReservationListByAccom(req);
			}
			case HOST -> {
				res = getReservationList(req);
			}
			case GUEST -> {
				res = getReservationListByUser(req);
			}
		}
		return res;
	}

	private Response getReservationListByAccom(Request req) {
		Object[] payload = (Object[]) req.getPayload();
		AccommodationDTO selectedAccom = (AccommodationDTO) payload[0];
		YearMonth nowMonth = (YearMonth) payload[1];
		List<ReservationDTO> reservationDTOS = null;
		if (nowMonth != null) {
			reservationDTOS = reservationService.checkReservationStatus(selectedAccom.getAccomID(), nowMonth);
		} else {
			reservationDTOS = reservationService.getReadyReservationList(selectedAccom); //승인대기중인 목록만 가져옴
		}
		return Response.builder().isSuccess(true).payload(reservationDTOS).build();
	}

	private Response getReservationListByUser(Request req) {
		UserDTO userDTO = (UserDTO) req.getPayload();
		List<ReservationDTO> reservationDTOS = reservationService.getReservationListByUserID(userDTO);
		return Response.builder().isSuccess(true).payload(reservationDTOS).build();
	}

	private Response getReservationList(Request request) {
		Object[] payload = (Object[]) request.getPayload();
		AccommodationDTO selectedAccom = (AccommodationDTO) payload[0];
		LocalDate nowMonth = (LocalDate) payload[1];
		List<ReservationDTO> reservationDTOS = null;
		if (nowMonth != null) {
			reservationDTOS = reservationService.getConfirmReservationList(selectedAccom, nowMonth);
		} else {
			reservationDTOS = reservationService.getReadyReservationList(selectedAccom); //승인대기중인 목록만 가져옴
		}
		return Response.builder().isSuccess(true).payload(reservationDTOS).build();
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
				res = putGuestReservation(req);
			}
			case GUEST -> {
			}
		}

		return res;
	}

	private Response putGuestReservation(Request req) {
		ReservationDTO reservationDTO = (ReservationDTO) req.getPayload();
		int column = reservationService.updateReservation(reservationDTO);
		return Response.builder().isSuccess(true).message("예약이 성공적으로 변경되었습니다.").build();
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
				res = requestReservationFromUser(req);
			}

		}

		return res;
	}

	private Response requestReservationFromUser(Request req) {
		ReservationDTO reservationDTO = (ReservationDTO) req.getPayload();
		if (reservationService.isReservationAvailable(reservationDTO)) {
			int charge = reservationService.calculateReservationCharge(reservationDTO);
			reservationDTO.setCharge(charge); //총요금 계산
			return Response.builder().isSuccess(true).message("예약이 성공적으로 등록되었습니다.").payload(reservationDTO).build();
		} else {
			return Response.builder().isSuccess(false).message("이미 해당 숙소가 예약중입니다.").build();
		}
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
