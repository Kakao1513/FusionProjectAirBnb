package Controller;

import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.UserDTO;
import service.AccommodationService;
import view.AccommodationView;

import java.util.List;

public class AccommodationController {
    private UserDTO currentUser;
    private AccommodationView accomView;
    private AccommodationService accomService;

    public AccommodationController(AccommodationView accomView, AccommodationService accomService){
        this.accomView = accomView;
        this.accomService = accomService;
    }

    public void accomMenu(){
        int order = accomView.displayAccomMenu();
        switch (order) {
            case 1 -> getAccomList();
            case 2 -> insertAccom();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }
    }

    public void getAccomList(){
        accomView.displayAccomList(accomService.selectAccom("승인됨"));
        System.out.println("1: 상세보기, 2: 필터로 검색");
        int order = accomView.getOrder();
        switch (order) {
            case 1 -> getAccomInfo();
            case 2 -> setSearchFilters();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }

    }

    public void getAccomInfo(){
        int accomID = accomView.getAccomNumberFromUser();
        AccommodationDTO curAccom = accomService.getAccom(accomID);
        accomView.displayAccomInfo(curAccom, accomService.getRate(accomID));
        accomView.displayAmenity(accomService.getAmenityList(accomID));
        accomView.displayReviews(accomService.getReviews(accomID));
        List<ReservationDTO> reservationDTOS = accomService.getReservationList(accomID, "20231101", "20231201");
        accomView.displayReservationCalendar(2023,11, curAccom, reservationDTOS);
    }

    public void setSearchFilters(){
        String accomName = null;
        String[] period = {null, null};
        String capacity = null;
        String accomType = null;
        while (true) {
            int order = accomView.displayFilterList();
            switch (order) {
                case 1 -> accomName = accomView.getAccomNameFromUser();
                case 2 -> period = accomView.getPeriodFromUser();
                case 3 -> capacity = accomView.getCapacityFromUser();
                case 4 -> accomType = accomView.getAccomTypeFromUser();
                default -> {
                    List<AccommodationDTO> filterdAccomList = accomService.selectAccom(
                            accomName,
                            period[0], period[1],
                            capacity,
                            accomType
                    );
                    accomView.displayAccomList(filterdAccomList);
                    return;
                }
            }
            accomView.displayAppliedFilters(
                    accomName,
                    period[0], period[1],
                    capacity,
                    accomType
            );
        }
    }

    public void insertAccom(){
        accomService.insertAccom(accomView.getAccomInfoFromUser());
    }
}
