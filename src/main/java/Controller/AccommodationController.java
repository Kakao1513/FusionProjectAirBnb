package Controller;

import Container.IocContainer;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccomMoreInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.*;
import service.AccommodationService;
import service.ReservationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AccommodationController implements MethodController {
	private final AccommodationService accomService;
	private final ReservationService reservationService;

	public AccommodationController(IocContainer iocContainer) {
		this.accomService = iocContainer.accommodationService();
		this.reservationService = iocContainer.reservationService();
	}

	public Response selectReadyAccomList() { //승인 대기중인 숙소 목록

		Response response = new Response();
		response.setIsSuccess(true);

		List<AccommodationDTO> curAccomList = accomService.selectAccom("Waiting");
		response.setPayload(curAccomList);
		return response;
	}

	public Response selectAccomList() { //관리자가 승인한 숙소 목록
		Response response = new Response();
		response.setIsSuccess(true);

		List<AccommodationDTO> curAccomList = accomService.selectAccom("Confirmed");
		response.setPayload(curAccomList);
		return response;
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

	private Response selectAccomMoreInfo(Request request) { //숙소 상세 정보 보기
		Object[] payload = (Object[]) request.getPayload();
		Integer accomID = (Integer) payload[0];
		LocalDate date = (LocalDate) payload[1];

		AccommodationDTO curAccom = accomService.selectAccomByAccomID(accomID);
		RatePolicyDTO accomRate = accomService.getRate(curAccom);
		List<AmenityDTO> amenityList = accomService.getAmenityList(curAccom);
		List<ReviewDTO> reviewList = accomService.getReviews(curAccom);
		List<ReservationDTO> reservationList = reservationService.getConfirmReservationList(curAccom, date);
		AccomMoreInfo accomMoreInfo = AccomMoreInfo.builder()
				.curAccom(curAccom)
				.accomRate(accomRate)
				.reservationList(reservationList)
				.reviewList(reviewList)
				.amenityList(amenityList).build();
		Response response = new Response();
		response.setIsSuccess(true);
		response.setPayload(accomMoreInfo);
		return response;
	}

	@Override
	public Response getHandle(Request req) {
		RoleType roleType = req.getRoleType();
		Response res = null;
		switch (roleType) {
			case COMMON -> {
				res = selectAccomMoreInfo(req);
			}
			case ADMIN -> {
				res = selectReadyAccomList();
			}
			case HOST -> {
				res = selectAccomByUser(req);
			}
			case GUEST -> {
				res = selectAccomList();
			}
		}
		return res;

	}
	private Response selectAccomByUser(Request req) {
		UserDTO userDTO = (UserDTO) req.getPayload();
		List<AccommodationDTO> myAccomList = accomService.selectAccomByUser(userDTO);
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

			}
			case HOST -> {
				res = registDiscountPolicy(req);
			}
			case GUEST -> { //숙소 필터링
				res = accomFiltering(req);
			}
		}
		return res;
	}

	private Response registDiscountPolicy(Request req) {
		DiscountPolicyDTO discountPolicyDTO = (DiscountPolicyDTO) req.getPayload();
		accomService.setDicountPolicy(discountPolicyDTO);
		AccommodationDTO accommodationDTO = accomService.selectAccomByAccomID(discountPolicyDTO.getAccomID());
		return Response.builder().payload(accommodationDTO).isSuccess(true).build();
	}


	@Override
	public Response postHandle(Request req) {
		RoleType roleType = req.getRoleType();
		Response res = null;
		switch (roleType) {
			case COMMON -> {
			}
			case ADMIN -> {
			}
			case HOST -> {
				res = setRatePolicy(req);
			}
			case GUEST -> {
			}

		}

		return res;
	}

	private Response setRatePolicy(Request req) {
		RatePolicyDTO ratePolicyDTO = (RatePolicyDTO) req.getPayload();
		accomService.updateRatePolicy(ratePolicyDTO);
		AccommodationDTO accommodationDTO = accomService.selectAccomByAccomID(ratePolicyDTO.getAccomID());
		return Response.builder().isSuccess(true).payload(accommodationDTO).build();
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

	public Response accomFiltering(Request req) {
		Map<String, Object> filters = (Map<String, Object>) req.getPayload();
		filters.put("status", "Confirmed");
		List<AccommodationDTO> acList = accomService.selectAccom(filters);
		Response rs = new Response();
		rs.setIsSuccess(true);
		rs.setPayload(acList);
		return rs;
	}
}


