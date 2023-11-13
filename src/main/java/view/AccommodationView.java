package view;

import persistence.dto.AccommodationDTO;

import java.util.List;

public class AccommodationView extends View<AccommodationDTO> {

    public void displayAccomMenu(){
        System.out.println("=========숙소 메뉴=========");
        System.out.println("(1) 숙소 예약");
        System.out.println("(2) 숙소 등록");
        System.out.println("(3) 나가기");
        System.out.print("================> ");
    }
    public void displayAccomList(List<AccommodationDTO> accommodationDTOS) {
        System.out.println("=========================================숙소 리스트========================================");
        System.out.println("|번호|      이름      |          주소          |  타입  | 인원 |             설명             |");
        System.out.println("------------------------------------------------------------------------------------------");
        for(AccommodationDTO accomDTO : accommodationDTOS){
            displayAccom(accomDTO);
        }
        System.out.println("===========================================================================================");
    }

    public void displayAccom(AccommodationDTO dto) {
        System.out.printf("|%-3d|%-14s|%-20s|%-7s|%-5s|%-25s|\n",
                dto.getAccomId(), dto.getAccomName(), dto.getAddress(), dto.getType(), dto.getCapacity(), dto.getComment());
    }

    public void displayInsertMenu(){

    }

}
