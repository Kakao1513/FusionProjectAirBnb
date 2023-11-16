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
        int order = accomView.displayAccomMenu();
        switch (order) {
            case 1 -> getAccomList();
            case 2 -> insertAccom();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }
    }

    public void getAccomList(){
        accomView.displayAccomList(accomService.getConfirmedAccomList());
        System.out.println("1: 상세보기, 2: 날짜로 검색");
        int order = accomView.getOrder();
        switch (order) {
            case 1 -> getAccomInfo();
            case 2 -> selectByDate();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }


    }

    public void getAccomInfo(){
        int accomID = accomView.getAccomNumberFromUser();
        accomView.displayAccomInfo(accomService.getAccom(accomID), accomService.getAmenityList(accomID));
    }

    public void selectByDate(){
        String[] period = accomView.getPeriodFromUser();
        accomView.displayAccomList(accomService.selectByDate(period[0], period[1]));
    }

    public void insertAccom(){
        accomService.insertAccom(accomView.getAccomInfoFromUser());
    }
}
