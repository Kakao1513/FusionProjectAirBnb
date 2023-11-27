package service;

import Container.IocContainer;
import persistence.dao.AccommodationDAO;
import persistence.dao.DiscountPolicyDAO;
import persistence.dao.RatePolicyDAO;
import persistence.dao.ReservationDAO;
import persistence.dto.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class ReservationService {
    private ReservationDAO reservationDAO;
    private RatePolicyDAO ratePolicyDAO;
    private DiscountPolicyDAO discountDAO;
    private AccommodationDAO accomDAO;


    public ReservationService(IocContainer iocContainer){
        this.reservationDAO = iocContainer.reservationDAO();
        this.accomDAO = iocContainer.accommodationDAO();
        this.ratePolicyDAO = iocContainer.ratePolicyDAO();
        this.discountDAO = iocContainer.discountPolicyDAO();
    }

    // 8 숙소별 월별 예약 현황 확인
    public List<ReservationDTO> checkReservationStatus(int accomId, YearMonth yearMonth)
    {
        Map<String, Object> filters = new HashMap<>();
        LocalDate date = yearMonth.atDay(1);

        filters.put("accomID", accomId);
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));

        return calculateReservationCharge(reservationDAO.getReservations(filters));
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

    public List<AccommodationDTO> getFilteredAccomList(Map<String, Object> filters){
        List<AccommodationDTO> accomDTOS = accomDAO.selectAccom(filters);
        List<AccommodationDTO> filteredAccomList = new ArrayList<>();

        for (AccommodationDTO accomDTO : accomDTOS) {
            if (isAccommodationAvailable(accomDTO, filters)) {
                filteredAccomList.add(accomDTO);
            }
        }

        return filteredAccomList;
    }


    private boolean isAccommodationAvailable(AccommodationDTO accomDTO, Map<String, Object> filters) {
        LocalDate startDate = (LocalDate)filters.get("checkIn");
        LocalDate endDate = (LocalDate)filters.get("checkOut");
        int term = (int)ChronoUnit.DAYS.between(startDate, endDate);
        int headcount = (int)filters.get("headcount");

        Map<String, Object> reservationFilters = new HashMap<>();
        reservationFilters.put("accomID", accomDTO.getAccomID());
        reservationFilters.put("checkIn", startDate);
        reservationFilters.put("checkOut", endDate);
        List<ReservationDTO> reservationDTOS = reservationDAO.getReservations(reservationFilters);

        int[] roomCount = new int[term + 1];

        for (ReservationDTO dto : reservationDTOS) {
            List<LocalDate> dateList = getDatesBetween(dto.getCheckIn(), dto.getCheckOut());

            for (LocalDate date : dateList) {
                int idx = (int) ChronoUnit.DAYS.between(startDate, date);
                roomCount[idx] += dto.getHeadCount();
            }
        }

        return accomDTO.getCapacity() - Arrays.stream(roomCount).max().getAsInt() >= headcount ||
                (accomDTO.getType().equals("공간 전체") && Arrays.stream(roomCount).max().getAsInt() == 0);
    }

    public boolean isReservationAvailable(ReservationDTO reservationDTO){
        AccommodationDTO accomDTO = accomDAO.getAccom(reservationDTO.getAccommodationID());
        Map<String, Object> filters = new HashMap<>();
        filters.put("headcount", accomDTO.getCapacity());
        filters.put("checkIn", reservationDTO.getCheckIn());
        filters.put("checkOut", reservationDTO.getCheckOut());
        return isAccommodationAvailable(accomDTO, filters);
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

    //14.2 해당 예약 날짜에 같은 방에 대한 다른 예약이 없다면 예약 성공
    synchronized public boolean reserveRequest(ReservationDTO userInputReserve) {

        //게스트
        //로그인 후 숙소을 예약할 수 있다(게스트 엔티티의 속성은 이름, 전화번호, 생년월일이다)
        //예약 절차
        //로그인
        //체크인, 체크아웃 날짜 입력(모든 숙소는 체크인 15:00, 체크아웃 11:00로 통일한다)
        //인원 입력(성인, 아동으로 구분하며 개인 숙소는 1인당 방 하나를 사용한다고 가정한다)
        //숙소 타입(1. 전체 2. 개인)
        //조건에 맞는 숙소 리스트 표시
        //숙소 리스트는 가격을 기준으로 오름차순과 내림차순으로 정렬 가능하다
        //리스트에서 숙소를 선택하면 숙소 상세 정보 + 후기 표시(1. 예약 2. 뒤로 가기)
        //예약을 선택하면 예약 완료 화면으로 이동한다

        if (isReservationAvailable(userInputReserve)){
            reservationDAO.insertReservation(userInputReserve);
            return true;
        } else {
            return false;
        }
    }
}
