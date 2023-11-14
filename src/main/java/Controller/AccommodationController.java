package Controller;

import persistence.dto.UserDTO;
import service.AccommodationService;
import view.AccommodationView;

import java.util.Scanner;

public class AccommodationController {
    private static final Scanner SCANNER = new Scanner(System.in);

    private UserDTO currentUser;
    private AccommodationView accomView;
    private AccommodationService accomService;

    public AccommodationController(AccommodationView accomView, AccommodationService accomService){
        this.accomView = accomView;
        this.accomService = accomService;
    }

    public void accomMenu(){
        accomView.displayAccomMenu();
        int choice = SCANNER.nextInt();
        switch (choice) {
            case 1 -> reserveAccom();
            case 2 -> insertAccom();
            default -> System.out.println("잘못 입력 하셨습니다.");
        }
    }

    public void reserveAccom(){
        accomView.displayAccomList(accomService.getConfirmedAccomList());
        int accomID = SCANNER.nextInt();
        accomView.displayAccomInfo(accomService.getAccom(accomID), accomService.getAmenityList(accomID));

    }

    public void insertAccom(){
        accomView.displayInsertMenu();
//        String accomName = SCANNER.next();
//        String address = SCANNER.next();
//        String type = SCANNER.next();
//        int capacity = SCANNER.nextInt();
//        String comment = SCANNER.next();

        accomService.insertAccom("테스트용 집", "테스트 주소", "테스트 타입",
                10, "테스트 코멘트");
    }
}
