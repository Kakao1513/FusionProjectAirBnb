package service;

import Container.IocContainer;
import persistence.dao.ReservationDAO;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.UserDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public ReservationService(IocContainer iocContainer){
        this.reservationDAO = iocContainer.reservationDAO();
    }

    // 4.2 숙박 예약 현황 보기(달력 화면 구성으로)
    public List<ReservationDTO> getReservationList(AccommodationDTO accomDTO, LocalDate date){
        Map<String, Object> filters = new HashMap<>();

        filters.put("accomID", accomDTO.getAccomId());
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));

        return reservationDAO.getReservations(filters);
    }


    // 5. 게스트의 숙박 예약 승인/거절
    // 15. (MyPage)예약 취소
    public int updateReservation(ReservationDTO rDTO){
        return reservationDAO.updateReservation(rDTO);
    }

    // 16. (MyPage)예약 현황 조회(완료된 숙박, 예약 대기 숙소, 예약된 숙소)
    public List<ReservationDTO> getReservationListByUserID(UserDTO user, LocalDate date){
        Map<String, Object> filters = new HashMap<>();

        filters.put("userID", user.getUserId());
        return reservationDAO.getReservations(filters);
    }

}
