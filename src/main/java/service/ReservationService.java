package service;

import Container.IocContainer;
import persistence.dao.DiscountPolicyDAO;
import persistence.dao.RatePolicyDAO;
import persistence.dao.ReservationDAO;
import persistence.dto.*;

import java.time.LocalDate;
import java.util.*;

public class ReservationService {
    private ReservationDAO reservationDAO;
    private RatePolicyDAO ratePolicyDAO;
    private DiscountPolicyDAO discountDAO;


    public ReservationService(IocContainer iocContainer){
        this.reservationDAO = iocContainer.reservationDAO();
        this.ratePolicyDAO = iocContainer.ratePolicyDAO();
        this.discountDAO = iocContainer.discountPolicyDAO();
    }

    // 4.2 숙박 예약 현황 보기(달력 화면 구성으로)
    // 13.4 숙소의 예약 가능 일자 확인
    public List<ReservationDTO> getConfirmReservationList(AccommodationDTO accomDTO, LocalDate date){
        Map<String, Object> filters = new HashMap<>();
        filters.put("accomID", accomDTO.getAccomID());
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));
        filters.put("status", "예약중");

        return reservationDAO.getReservations(filters);
    }
    public List<ReservationDTO> getReadyReservationList(AccommodationDTO accomDTO) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("accomID", accomDTO.getAccomID());
        filters.put("status", "승인대기중");

        return calculateReservationCharge(reservationDAO.getReservations(filters));
    }

    // 숙박이 완료된 예약 조회, 리뷰 등록을 위해 사용됨
    public List<ReservationDTO> completedReservationList(AccommodationDTO accomDTO) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("now", LocalDate.now());
        filters.put("status", "예약중");

        return calculateReservationCharge(reservationDAO.getReservations(filters));
    }

    // 15. (MyPage)예약 취소
    public int updateReservation(ReservationDTO rDTO){
        return reservationDAO.updateReservation(rDTO);
    }

    // 16. (MyPage)예약 현황 조회(완료된 숙박, 예약 대기 숙소, 예약된 숙소) -> 한번에 조회할 수 있게 만들
    public List<ReservationDTO> getReservationListByUserID(UserDTO user){
        Map<String, Object> filters = new HashMap<>();
        filters.put("userID", user.getUserId());
      //  filters.put("status", status);

        return calculateReservationCharge(reservationDAO.getReservations(filters));
    }

    private List<ReservationDTO> calculateReservationCharge(List<ReservationDTO> reservationDTOS){
        if (reservationDTOS != null){
            for(ReservationDTO reservationDTO : reservationDTOS){
                reservationDTO.setCharge(calculateReservationCharge(reservationDTO));
                if(reservationDTO.getCheckOut().isBefore(LocalDate.now()) && reservationDTO.getReservationInfo().equals("예약중"))
                    reservationDTO.setReservationInfo("숙박 완료");
            }
        }
        return reservationDTOS;
    }

    public int calculateReservationCharge(ReservationDTO rDTO){
        LocalDate startDate = rDTO.getCheckIn();
        LocalDate endDate = rDTO.getCheckOut();
        RatePolicyDTO rateDTO = ratePolicyDAO.getRate(rDTO.getAccommodationID());
        List<DiscountPolicyDTO> discountDTOS = discountDAO.getDiscount(rDTO.getAccommodationID());

        List<LocalDate> datesInRange = getDatesBetween(startDate, endDate);

        int sumCharge = 0;

        for (LocalDate date : datesInRange) {
            int charge = 0;

            if(isWeekday(date))
                charge = rateDTO.getWeekday();
            else
                charge = rateDTO.getWeekend();

            if (discountDTOS != null){
                for(DiscountPolicyDTO discountDTO : discountDTOS){
                    if(date.isAfter(discountDTO.getDateStart()) && date.isBefore(discountDTO.getDateEnd())){
                        if(Objects.equals(discountDTO.getDiscountType(), "정량")){
                            charge -= discountDTO.getValue();
                        }
                        else {
                            charge *= (100 - discountDTO.getValue()) * 0.01;
                        }
                    }
                }
            }

            sumCharge += charge;

        }
        return sumCharge;
    }

    private static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> datesInRange = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            datesInRange.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return datesInRange;
    }

    private static boolean isWeekday(LocalDate date) {
        // 월요일(1)부터 금요일(5)까지가 주중
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 5;
    }

    // 14. 숙소 예약 신청(to 호스트). 단, 일정이 중복된 예약을 시도할 때,
    // 적절한 메시지와 함께 예약 이 불가함을 보임. 예약 신청 시 게스트는 총 요금을 확인할 수 있다

    // 14.1 예약 가능한 방 목록을 조회
    public List<RoomDTO> getAvailableRoomList(ReservationDTO reservationDTO){
        return reservationDAO.getAvailableRoomList(reservationDTO);
    }
    //14.2 해당 예약 날짜에 같은 방에 대한 다른 예약이 없다면 예약 성공
    synchronized public boolean reserveRequest(ReservationDTO userInputReserve) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("accomID", userInputReserve.getReservationID());
        filters.put("roomID", userInputReserve.getRoomID());
        filters.put("checkIn", userInputReserve.getCheckIn());
        filters.put("checkOut", userInputReserve.getCheckOut());

        List<ReservationDTO> reserves = reservationDAO.getReservations(filters);
        if (reserves.isEmpty()) { //해당 날짜에 예약된 방이 없음을 의미.
            reservationDAO.insertReservation(userInputReserve);
            return true;
        } else {
            return false;
        }
    }



}
