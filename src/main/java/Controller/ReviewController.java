package Controller;

import Container.IocContainer;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.RoleType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReviewDTO;
import persistence.mapper.ReviewSQL;
import service.AccommodationService;
import service.ReservationService;
import service.UserService;

public class ReviewController implements MethodController {
    private final ReservationService reservationService;

    private final AccommodationService accommodationService;
    private final UserService userService;

    public ReviewController(IocContainer iocContainer) {
        this.reservationService = iocContainer.reservationService();
        this.accommodationService = iocContainer.accommodationService();
        this.userService = iocContainer.userService();
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
                res = postReview(req);
            }
        }
        return res;
    }

    private Response postReview(Request req) {
        ReviewDTO reviewDTO = (ReviewDTO) req.getPayload();
        Response response = null;
        try {
            userService.insertReview(reviewDTO);
            response = Response.builder().isSuccess(true).build();
        } catch (Exception e) {
            response = Response.builder().isSuccess(false).message("리뷰 등록에 실패하였습니다.").build();
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
