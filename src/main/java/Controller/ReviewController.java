package Controller;

import Container.IocContainer;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.RoleType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.ReviewDTO;
import service.AccommodationService;
import service.ReservationService;
import service.ReviewService;
import service.UserService;

import java.util.List;

public class ReviewController implements MethodController {
	private final ReservationService reservationService;

	private final AccommodationService accommodationService;
	private final UserService userService;
	private final ReviewService reviewService;

	public ReviewController(IocContainer iocContainer) {
		this.reservationService = iocContainer.reservationService();
		this.accommodationService = iocContainer.accommodationService();
		this.userService = iocContainer.userService();
		this.reviewService = iocContainer.reviewService();
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
				res = getReviewsByAccommodation(req);
			}
			case GUEST -> {
			}
		}
		return res;
	}

	private Response getReviewsByAccommodation(Request req) {
		AccommodationDTO accom = (AccommodationDTO) req.getPayload();
		Response response = null;
		try {
			List<ReviewDTO> reviewDTOS = reviewService.getReviews(accom);
			response = Response.builder().isSuccess(true).payload(reviewDTOS).build();
		} catch (Exception e) {
			response = Response.builder().isSuccess(false).message("후기를 불러오는데 실패했습니다.").build();
		}
		return response;
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
				res = postReply(req);
			}
			case GUEST -> {
				res = postReview(req);
			}
		}
		return res;
	}

	private Response postReply(Request req) {
		ReviewDTO reply = (ReviewDTO) req.getPayload();
		Response response = null;
		try {
			reviewService.insertReview(reply);
			response = Response.builder().isSuccess(true).build();
		} catch (Exception e) {
			response = Response.builder().isSuccess(false).message("이미 답글이 작성되었습니다.").build();
		}
		return response;
	}

	private Response postReview(Request req) {
		ReviewDTO reviewDTO = (ReviewDTO) req.getPayload();
		Response response = null;
		try {
			reviewService.insertReview(reviewDTO);
			response = Response.builder().isSuccess(true).build();
		} catch (Exception e) {
			response = Response.builder().isSuccess(false).message("이미 후기가 작성되었습니다.").build();
		}
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
