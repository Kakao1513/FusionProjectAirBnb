package view;

import persistence.dto.AccommodationDTO;
import persistence.dto.AmenityDTO;

import java.util.List;
import java.util.Scanner;

public class AccommodationView extends View<AccommodationDTO> {
    private static final Scanner SCANNER = new Scanner(System.in);

    public int displayAccomMenu(){
        System.out.println("=========숙소 메뉴=========");
        System.out.println("(1) 숙소 예약");
        System.out.println("(2) 숙소 등록");
        System.out.println("(3) 나가기");
        System.out.println("==========================");

        System.out.print("선택할 번호를 입력하세요 : ");
        return SCANNER.nextInt();
    }
    public void displayAccomList(List<AccommodationDTO> accommodationDTOS) {
        System.out.println("=========================================숙소 리스트========================================");
        System.out.println("|번호|      이름      |          주소          |  타입  | 인원 |             설명             |");
        System.out.println("------------------------------------------------------------------------------------------");
        for(AccommodationDTO dto : accommodationDTOS){
            System.out.printf("|%-3d|%-14s|%-20s|%-7s|%-5s|%-25s|\n",
                    dto.getAccomId(), dto.getAccomName(), dto.getAddress(),
                    dto.getType(), dto.getCapacity(), dto.getComment());
        }
        System.out.println("===========================================================================================");
    }


    public int getOrder(){
        System.out.print("선택할 번호를 입력하세요 : ");
        return SCANNER.nextInt();
    }

    public int getAccomNumberFromUser(){
        System.out.print("선택할 숙소 번호를 입력하세요 : ");
        return SCANNER.nextInt();
    }


    public int displayFilterList(){
        System.out.println("==========검색 필터==========");
        System.out.println("1. 숙소 이름으로 검색");
        System.out.println("2. 날짜로 검색");
        System.out.println("3. 인원 수로 검색");
        System.out.println("4. 숙소 타입으로 검색");
        System.out.println("5. 완료");
        System.out.println("============================");
        System.out.print("사용할 필터를 선택하세요 : ");
        return SCANNER.nextInt();
    }

    public void displayAppliedFilters(
            String accomName,
            String startDate, String endDate,
            String capacity,
            String accomType
    ){
        System.out.println("==========적용된 필터==========");
        if (accomName != null){
            System.out.println("숙소 이름 : " + accomName);
        }
        if (startDate != null){
            System.out.println("검색 날짜 : " + startDate + " ~ " + endDate);
        }
        if (capacity != null){
            System.out.println("최소 수용 인원 : " + capacity);
        }
        if (accomType != null){
            System.out.println("숙소 타입 : " + accomType);
        }
    }

    public String getAccomNameFromUser(){
        String accomName;
        System.out.print("숙소 이름 : ");
        accomName = SCANNER.next();

        return "%"+accomName+"%";
    }

    public String getCapacityFromUser(){
        String capacity;
        System.out.print("수용 인원 : ");
        capacity = SCANNER.next();

        return capacity;
    }
    public String getAccomTypeFromUser(){
        String accomType;
        System.out.print("숙소 타입 : ");
        accomType = SCANNER.next();

        return accomType;
    }


    public String[] getPeriodFromUser(){
        String startDate, endDate;
        System.out.print("시작 날짜 : ");
        startDate = SCANNER.next();
        System.out.print("끝 날짜 : ");
        endDate = SCANNER.next();

        return new String[]{startDate, endDate};
    }

    public void displayAccomInfo(AccommodationDTO accomDTO, List<AmenityDTO> amenityDTOS){

        System.out.printf("==========[%3d] %s==========\n",accomDTO.getAccomId(), accomDTO.getAccomName());
        System.out.println("위치 : " + accomDTO.getAddress());
        System.out.println("설명 : " + accomDTO.getComment());
        displayAmenity(amenityDTOS);
    }

    public void displayAmenity(List<AmenityDTO> amenityDTOS){
        System.out.println("=========편의시설 리스트=========");
        for(AmenityDTO dto : amenityDTOS){
            System.out.println(dto.getName());
        }
        System.out.println("==============================");

    }

    public AccommodationDTO getAccomInfoFromUser() {
        System.out.print("숙소 이름을 입력하세요: ");
        String accomName = SCANNER.next();

        System.out.print("숙소 주소를 입력하세요: ");
        String address = SCANNER.next();

        System.out.print("숙소 타입을 입력하세요: ");
        String type = SCANNER.next();

        System.out.print("수용 인원을 입력하세요: ");
        int capacity = SCANNER.nextInt();

        System.out.print("숙소 설명을 입력하세요: ");
        String comment = SCANNER.next();

        return AccommodationDTO.builder()
                .userID(1)
                .accomName(accomName)
                .address(address)
                .type(type)
                .capacity(capacity)
                .comment(comment)
                .status("대기중")
                .build();
    }

}
