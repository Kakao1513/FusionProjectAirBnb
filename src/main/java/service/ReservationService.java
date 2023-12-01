package service;

import Container.IocContainer;
import network.Protocol.Packet.AccommodationCharge;
import persistence.dao.AccommodationDAO;
import persistence.dao.DiscountPolicyDAO;
import persistence.dao.RatePolicyDAO;
import persistence.dao.ReservationDAO;
import persistence.dto.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

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
        filters.put("reservationInfo", "예약중");

        return calculateReservationsCharge(reservationDAO.getReservations(filters));
    }

    // 9 숙소별 월별 총매출 확인
    public int getTotalSalesByAccom(AccommodationDTO accomDTO, YearMonth yearMonth){
        LocalDate firstDayOfMonth = yearMonth.atDay(1);

        List<ReservationDTO> reservationDTOS = getConfirmReservationList(accomDTO, firstDayOfMonth);

        int sum = 0;

        for(ReservationDTO reservationDTO : reservationDTOS){
            LocalDate startDate = reservationDTO.getCheckIn();
            LocalDate endDate = reservationDTO.getCheckOut();

            if(startDate.isBefore(firstDayOfMonth))
                startDate = firstDayOfMonth;
            if(endDate.isAfter(yearMonth.atEndOfMonth()))
                endDate = firstDayOfMonth.plusMonths(1);

            sum += calculateReservationCharge(reservationDTO, startDate, endDate);

        }

        return sum;
    }

    // 4.2 숙박 예약 현황 보기(달력 화면 구성으로)
    // 13.4 숙소의 예약 가능 일자 확인
    public List<ReservationDTO> getConfirmReservationList(AccommodationDTO accomDTO, LocalDate date){
        Map<String, Object> filters = new HashMap<>();
        filters.put("accomID", accomDTO.getAccomID());
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));
        filters.put("reservationInfo", "예약중");

        return calculateReservationsCharge(reservationDAO.getReservations(filters));
    }
    public List<ReservationDTO> getReadyReservationList(AccommodationDTO accomDTO) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("accomID", accomDTO.getAccomID());
        filters.put("reservationInfo", "승인대기중");

        return calculateReservationsCharge(reservationDAO.getReservations(filters));
    }



    // 15. (MyPage)예약 취소
    public int updateReservation(ReservationDTO rDTO){
        return reservationDAO.updateReservation(rDTO);
    }

    // 16. (MyPage)예약 현황 조회(완료된 숙박, 예약 대기 숙소, 예약된 숙소) -> 한번에 조회할 수 있게 만들
    public List<ReservationDTO> getReservationListByUserID(UserDTO user){
        Map<String, Object> filters = new HashMap<>();
        filters.put("userID", user.getUserId());
      //  filters.put("reservationInfo", status);

        return calculateReservationsCharge(reservationDAO.getReservations(filters));
    }

    private List<ReservationDTO> calculateReservationsCharge(List<ReservationDTO> reservationDTOS){
        for(ReservationDTO reservationDTO : reservationDTOS){
            reservationDTO.setCharge(calculateReservationCharge(reservationDTO));

            if(reservationDTO.getCheckOut().isBefore(LocalDate.now()) && reservationDTO.getReservationInfo().equals("예약중"))
                reservationDTO.setReservationInfo("숙박 완료");
        }
        return reservationDTOS;
    }

    public int calculateReservationCharge(ReservationDTO reservationDTO){
        LocalDate startDate = reservationDTO.getCheckIn();
        LocalDate endDate = reservationDTO.getCheckOut();

        return calculateReservationCharge(reservationDTO, startDate, endDate);
    }

    public int calculateReservationCharge(ReservationDTO rDTO, LocalDate startDate, LocalDate endDate){
        AccommodationDTO accomDTO = accomDAO.getAccom(rDTO.getAccommodationID());
        RatePolicyDTO rateDTO = ratePolicyDAO.getRate(rDTO.getAccommodationID());
        List<DiscountPolicyDTO> discountDTOS = discountDAO.getDiscount(rDTO.getAccommodationID());

        List<LocalDate> datesInRange = getDatesBetween(startDate, endDate);

        int sumCharge = 0;

        for (LocalDate date : datesInRange) {
            int charge = isWeekday(date) ? rateDTO.getWeekday() : rateDTO.getWeekend();

            if (discountDTOS != null){
                for(DiscountPolicyDTO discountDTO : discountDTOS){
                    // 할인 기간일 경우
                    if(!date.isBefore(discountDTO.getStartDate()) && !date.isAfter(discountDTO.getEndDate())){
                        if(discountDTO.getDiscountType().equals("정량")){
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

        if (accomDTO.getType().equals("개인실")){
            sumCharge *= rDTO.getHeadcount();
        }

        return sumCharge;
    }

/*
 //원래 메서드
    public List<AccommodationDTO> getFilteredAccomList(Map<String, Object> filters){
        filters.put("capacity", filters.get("headcount"));
        filters.put("notCancel", true);
        List<AccommodationDTO> accomDTOS = accomDAO.selectAccom(filters);
        List<ReservationDTO> reservationDTOS = reservationDAO.getReservations(filters);
        List<AccommodationDTO> filteredAccomList = new ArrayList<>();

        for (AccommodationDTO accomDTO : accomDTOS) {
            if (isAccommodationAvailable(accomDTO, filterReservationsByAccomID(reservationDTOS, accomDTO.getAccomID()), filters)) {
                filteredAccomList.add(accomDTO);
            }
        }

        return filteredAccomList;
    }
*/

    public List<AccommodationCharge> getFilteredAccomList(Map<String, Object> filters){
        filters.put("capacity", filters.get("headcount"));
        filters.put("notCancel", true);
        List<AccommodationDTO> accomDTOS = accomDAO.selectAccom(filters);
        List<ReservationDTO> reservationDTOS = reservationDAO.getReservations(filters);
        List<AccommodationDTO> filteredAccomList = new ArrayList<>();

        for (AccommodationDTO accomDTO : accomDTOS) {
            if (isAccommodationAvailable(accomDTO, filterReservationsByAccomID(reservationDTOS, accomDTO.getAccomID()), filters)) {
                filteredAccomList.add(accomDTO);
            }
        }

        return setChargeToAccomList(filteredAccomList, filters);
    }

    private List<AccommodationCharge> setChargeToAccomList(List<AccommodationDTO> accomDTOS, Map<String, Object> filters){
        List<AccommodationCharge> accommodationChargeList = new ArrayList<>();
        for(AccommodationDTO accomDTO : accomDTOS){
            ReservationDTO reservationDTO = ReservationDTO.builder()
                    .accommodationID(accomDTO.getAccomID())
                    .headcount((int)filters.get("headcount"))
                    .checkIn((LocalDate) filters.get("checkIn"))
                    .checkOut((LocalDate)filters.get("checkOut")).build();

            int charge = calculateReservationCharge(reservationDTO);
            accommodationChargeList.add(AccommodationCharge.builder().accom(accomDTO).charge(charge).build());
        }

        return accommodationChargeList;
    }


    private boolean isAccommodationAvailable(AccommodationDTO accomDTO, List<ReservationDTO> reservationDTOS, Map<String, Object> filters) {
        LocalDate startDate = (LocalDate)filters.get("checkIn");
        LocalDate endDate = (LocalDate)filters.get("checkOut");
        int term = (int)ChronoUnit.DAYS.between(startDate, endDate);
        int headcount = (int)filters.get("headcount");

        int[] roomCount = new int[term + 1];

        for (ReservationDTO dto : reservationDTOS) {
            LocalDate checkIn = dto.getCheckIn();
            LocalDate checkOut = dto.getCheckOut();

            if (checkIn.isBefore(startDate))
                checkIn = startDate;
            if (checkOut.isAfter(endDate))
                checkOut = endDate;

            List<LocalDate> dateList = getDatesBetween(checkIn, checkOut);

            for (LocalDate date : dateList) {
                int idx = (int) ChronoUnit.DAYS.between(startDate, date);
                roomCount[idx] += dto.getHeadcount();
            }
        }
        int max = Arrays.stream(roomCount).max().getAsInt();

        return (accomDTO.getType().equals("공간 전체") && max == 0) || accomDTO.getCapacity() - max  >= headcount;
    }

    public boolean isReservationAvailable(ReservationDTO reservationDTO){
        AccommodationDTO accomDTO = accomDAO.getAccom(reservationDTO.getAccommodationID());
        Map<String, Object> filters = new HashMap<>();
        filters.put("headcount", reservationDTO.getHeadcount());
        filters.put("checkIn", reservationDTO.getCheckIn());
        filters.put("checkOut", reservationDTO.getCheckOut());
        filters.put("accomID", accomDTO.getAccomID());
        filters.put("notCancel", true);
        List<ReservationDTO> reservationDTOS = reservationDAO.getReservations(filters);

        return isAccommodationAvailable(accomDTO, reservationDTOS, filters);
    }

    private List<ReservationDTO> filterReservationsByAccomID(List<ReservationDTO> reservationDTOS, int accomID){
        return reservationDTOS.stream()
                .filter(reservationDTO -> reservationDTO.getAccommodationID() == accomID)
                .collect(Collectors.toList());
    }


    private static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> datesInRange = new ArrayList<>();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate.minusDays(1))) {
            datesInRange.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        return datesInRange;
    }

    private static boolean isWeekday(LocalDate date) {
        // 월요일(1)부터 목요일(4)까지가 주중
        int dayOfWeek = date.getDayOfWeek().getValue();
        return dayOfWeek >= 1 && dayOfWeek <= 4;
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
        if (isReservationAvailable(userInputReserve)) {
            return reservationDAO.insertReservation(userInputReserve) != 0;
        }
        return false;
    }

    // 9 숙소별 월별 총매출 확인
    public int checkTotalSales(int accomID, YearMonth month) { //안됨
        Map<String, Object> filters = new HashMap<>();
        LocalDate date = month.atDay(1);

        filters.put("reservationInfo", "예약중");
        filters.put("accomID", accomID);
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));

        List<ReservationDTO> reservationList = reservationDAO.getReservations(filters); // reservation list
        List<Integer> rates = getRatesFromReservations(reservationList, accomID, month); // list's rate

        return rates.stream().mapToInt(Integer::intValue).sum();
    }

    // 9.1 총매출 계산
    private List<Integer> getRatesFromReservations(List<ReservationDTO> reservationList, int accomID, YearMonth yearMonth) {
        List<Integer> rates = new ArrayList<>();
        AccommodationDTO accommodationDTO = accomDAO.getAccom(accomID);
        List<DiscountPolicyDTO> discountDTOS = discountDAO.getDiscount(accomID);

        for (ReservationDTO reservation : reservationList) {
            LocalDate curDate = reservation.getCheckIn();
            if(curDate.isBefore(yearMonth.atDay(1)))
                curDate = yearMonth.atDay(1);
            LocalDate endDate = reservation.getCheckOut();
            if(endDate.isAfter(yearMonth.atEndOfMonth()))
                endDate = yearMonth.atEndOfMonth();

            while (curDate.isBefore(endDate)) {
                int charge = getCharge(reservation, discountDTOS, curDate);
                if(accommodationDTO.getType().equals("개인실"))
                    charge *= reservation.getHeadcount();
                rates.add(charge);
                curDate = curDate.plusDays(1);
            }
        }
        return rates;
    }

    // 9.2 할인 적용된 값 계산
    private int getCharge(ReservationDTO reservation, List<DiscountPolicyDTO> discountDTOS, LocalDate curDate)
    {
        RatePolicyDTO ratePolicyDTO = ratePolicyDAO.getRate(reservation.getAccommodationID());
        int charge = isWeekdayCharge(ratePolicyDTO, curDate);

        // 할인이 있다면
        if (discountDTOS != null) {
            for (DiscountPolicyDTO discountDTO : discountDTOS) {
                if (curDate.isAfter(discountDTO.getStartDate().minusDays(1)) && curDate.isBefore(discountDTO.getEndDate().plusDays(1))) {
                    if (Objects.equals(discountDTO.getDiscountType(), "정량")) {
                        charge -= discountDTO.getValue();
                    }
                    else {
                        charge *= (int) ((100 - discountDTO.getValue()) * 0.01);
                    }
                }
            }
        }
        return charge;
    }

    // 9.3 주말 주일 가격
    private int isWeekdayCharge(RatePolicyDTO ratePolicyDTO, LocalDate date) {
        int dayOfWeek = date.getDayOfWeek().getValue();
        boolean isWeekend = (dayOfWeek == 5 || dayOfWeek == 6 || dayOfWeek == 7);

        return isWeekend ? ratePolicyDTO.getWeekend() : ratePolicyDTO.getWeekday();
    }

}
