package Controller;

import Enums.AccommodationStatus;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.UserDTO;
import persistence.dto.RatePolicyDTO;
import service.AccommodationService;
import view.AccommodationView;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AccommodationController {
    private UserDTO currentUser;
    private AccommodationView accomView;
    private AccommodationService accomService;
    private AccommodationStatus status;
    private Scanner sc = new Scanner(System.in);

    public AccommodationController(AccommodationService accomService){
        this.accomService = accomService;
    }

    public void accomMenu(){
        int order = accomView.displayAccomMenu();
        switch (order) {
            case 1 -> accomListMenu();
            case 2 -> insertAccom();
            case 3 -> setAccomPolicy();
            case 4 -> setAccomDiscount();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }
    }

    public void accomListMenu(){
        List<AccommodationDTO> curAccomList = accomService.selectAccom("승인됨");
        while(true){
            accomView.displayAccomList(curAccomList);
            int order = accomView.getOrder();
            switch (order) {
                case 1 -> getAccomInfo();
                case 2 -> curAccomList = setSearchFilters();
                default -> {
                    System.out.println("<<종료>>");
                    return;
                }
            }
        }
    }

    public void getAccomInfo(){
        int accomID = accomView.getAccomNumberFromUser();
        AccommodationDTO curAccom = accomService.getAccom(accomID);
        if (curAccom != null){
            accomView.displayAccomInfo(curAccom, accomService.getRate(curAccom));
            accomView.displayAmenity(accomService.getAmenityList(curAccom));
            accomView.displayReviews(accomService.getReviews(curAccom));
            LocalDate date = accomView.getReservationDate();
            List<ReservationDTO> reservationDTOS = accomService.getReservationList(curAccom, date);
            accomView.displayReservationCalendar(date, curAccom.getCapacity(), reservationDTOS);
        }
    }

    public List<AccommodationDTO> setSearchFilters(){
        Map<String, Object> filters = new HashMap<>();
        filters.put("status", "승인됨");
        while (true) {
            int order = accomView.displayFilterList();
            switch (order) {
                case 1 -> filters.put("accomName", accomView.getAccomNameFromUser());
                case 2 -> filters.put("period", accomView.getPeriodFromUser());
                case 3 -> filters.put("capacity", accomView.getCapacityFromUser());
                case 4 -> filters.put("accomType", accomView.getAccomTypeFromUser());
                default -> {
                    return accomService.selectAccom(filters);
                }
            }
        }
    }

    public void insertAccom(){
        accomService.insertAccom(accomView.getAccomInfoFromUser());
    }

    private void setAccomPolicy() {
        List<AccommodationDTO> curAccomList = accomService.selectAccom("승인됨");
        while(true){
            accomView.displayAccomList(curAccomList);
            accomView.Return();
            int order = accomView.getAccomNumberFromUser();
            if (order == 0) {
                accomMenu();
                return;
            } else {
              //TODO:  accomService.setAccomPolicy(accomView.getRatePolicyFromUser(order));
            }
        }
    }

    public void setAccomDiscount() {
        List<AccommodationDTO> curAccomList = accomService.selectAccom("승인됨");
        while(true){
            accomView.displayAccomList(curAccomList);
            accomView.Return();
            int order = accomView.getDailyOrDiscount();
            switch (order) {
                case 1 -> getAccomInfo();
                case 2 -> curAccomList = setSearchFilters();
                default -> {
                    System.out.println("<<종료>>");
                    return;
                }
            }
        }
    }
    
    public void updateAccomStatus() {
        System.out.println("숙소 등록 (1.승인 2.거절): ");
        int id = sc.nextInt();
        int statusIdx = sc.nextInt();
        
        switch (statusIdx) {
            case 1->{
                status = AccommodationStatus.Confirmed;
                accomService.updateAccomStatus(id, status);
            }
            case 2->{
                status = AccommodationStatus.Refused;
                accomService.updateAccomStatus(id, status);
            }
            default ->{
                System.out.println("잘못된 입력입니다.");
            }
        }
    }
    
    public void checkReservationStatus() {
        System.out.println("확인하고 싶은 월 입력 : ");
        int month = sc.nextInt();
        
        if (!(1 <= month && month <= 12))
            System.out.println("올바른 값을 입력하세요.");
        else {
            LocalDate date = LocalDate.now().withMonth(month);
            int accomID = accomView.getAccomNumberFromUser();
            
            List<ReservationDTO> reservationList = accomService.checkReservationStatus(accomID, month);
            accomView.displayReservationCalendar(date, reservationList.size(), reservationList);
        }
    }
}
