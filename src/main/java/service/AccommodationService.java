package service;

import persistence.dao.AccommodationDAO;
import persistence.dao.AmenityDAO;
import persistence.dto.AccommodationDTO;
import persistence.dto.AmenityDTO;

import java.util.List;

public class AccommodationService {
    private AccommodationDAO accomDAO;
    private AmenityDAO amenityDAO;
    public AccommodationService(AccommodationDAO accomDAO){
        this.accomDAO = accomDAO;
    }
    public AccommodationService(AccommodationDAO accomDAO, AmenityDAO amenityDAO){
        this.accomDAO = accomDAO;
        this.amenityDAO = amenityDAO;
    }

    public List<AccommodationDTO> getConfirmedAccomList(){
        return accomDAO.selectAccom("승인됨", null, null, null, null, null);
    }

    public List<AccommodationDTO> getPendingAccomList(){
        return accomDAO.selectAccom("대기중", null, null, null, null, null);
    }

    public List<AccommodationDTO> selectAccom(String status,
                                              String accomName,
                                              String startDate, String endDate,
                                              String capacity,
                                              String accomType){
        return accomDAO.selectAccom(status, accomName, startDate, endDate, capacity, accomType);
    }

    public AccommodationDTO getAccom(int accomID){
        return accomDAO.getAccom(accomID);
    }

    public List<AmenityDTO> getAmenityList(int accomID){
        return amenityDAO.getAmenity(accomID);
    }

    public void insertAccom(AccommodationDTO accomDTO){
        accomDAO.insertAccom(accomDTO);
    }

}
