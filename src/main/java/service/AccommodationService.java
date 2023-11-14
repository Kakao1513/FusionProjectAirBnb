package service;

import persistence.dao.AccommodationDAO;
import persistence.dao.AmenityDAO;
import persistence.dto.AccommodationDTO;
import persistence.dto.AmenityDTO;

import java.util.List;

public class AccommodationService {
    private AccommodationDAO accomDAO;
    private AmenityDAO amenityDAO;

    public AccommodationService(AccommodationDAO accomDAO, AmenityDAO amenityDAO){
        this.accomDAO = accomDAO;
        this.amenityDAO = amenityDAO;
    }


    public List<AccommodationDTO> getConfirmedAccomList(){
        return accomDAO.selectByStatus("승인됨");
    }

    public List<AccommodationDTO> getPendingAccomList(){
        return accomDAO.selectByStatus("대기중");
    }

    public AccommodationDTO getAccom(int accomID){
        return accomDAO.getAccom(accomID);
    }

    public List<AmenityDTO> getAmenityList(int accomID){
        return amenityDAO.getAmenity(accomID);
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
