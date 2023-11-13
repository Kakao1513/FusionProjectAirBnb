package service;

import persistence.dao.AccommodationDAO;
import persistence.dto.AccommodationDTO;

import java.util.List;

public class AccommodationService {
    private AccommodationDAO accomDAO;

    public AccommodationService(AccommodationDAO accomDAO){
        this.accomDAO = accomDAO;
    }


    public List<AccommodationDTO> getConfirmedAccomList(){
        return accomDAO.selectConfirm();
    }

    public void insertAccom(String accomName, String address, String type, int capacity, String comment){

        AccommodationDTO accomDTO = AccommodationDTO.builder()
                .userID(1)
                .accomName(accomName)
                .address(address)
                .type(type)
                .capacity(capacity)
                .comment(comment)
                .status("대기중")
                .build();

        accomDAO.insertAccom(accomDTO);
    }

}
