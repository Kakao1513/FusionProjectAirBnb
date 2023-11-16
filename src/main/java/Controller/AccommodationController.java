package Controller;

import persistence.dto.UserDTO;
import service.AccommodationService;
import view.AccommodationView;

public class AccommodationController {
    private UserDTO currentUser;
    private AccommodationView accomView;
    private AccommodationService accomService;

    public AccommodationController(AccommodationView accomView, AccommodationService accomService){
        this.accomView = accomView;
        this.accomService = accomService;
    }

    public void accomMenu(){
        int choice = accomView.displayAccomMenu();
        switch (choice) {
            case 1 -> reserveAccom();
            case 2 -> insertAccom();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }
    }

    public void reserveAccom(){
        accomView.displayAccomList(accomService.getConfirmedAccomList());
        int accomID = accomView.getAccomNumberFromUser();
        accomView.displayAccomInfo(accomService.getAccom(accomID), accomService.getAmenityList(accomID));

    }

    public void insertAccom(){
        accomService.insertAccom(accomView.getAccomInfoFromUser());
    }
}
