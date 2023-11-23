package service;

import Enums.AccommodationStatus;
import persistence.dao.*;
import persistence.dto.*;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccommodationService {
    private AccommodationDAO accomDAO;
    private AmenityDAO amenityDAO;
    private RatePolicyDAO ratePolicyDAO;
    private ReviewDAO reviewDAO;
    public AccommodationService(AccommodationDAO accomDAO, AmenityDAO amenityDAO,
                                ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, ReviewDAO reviewDAO){
        this.accomDAO = accomDAO;
        this.amenityDAO = amenityDAO;
        this.ratePolicyDAO = ratePolicyDAO;
        this.reviewDAO = reviewDAO;
    }

    public List<AccommodationDTO> selectAccom(Integer userID){
        Map<String, Object> filters = new HashMap<>();
        filters.put("userID", userID);
        return accomDAO.selectAccom(filters);
    }

    public List<AccommodationDTO> selectAccom(String status){
        Map<String, Object> filters = new HashMap<>();
        filters.put("status", status);
        return accomDAO.selectAccom(filters);
    }

    public List<AccommodationDTO> selectAccom(Map<String, Object> filters){
        return accomDAO.selectAccom(filters);
    }

    public AccommodationDTO getAccom(int accomID){
        return accomDAO.getAccom(accomID);
    }

    public void insertAccom(AccommodationDTO accomDTO){
        accomDAO.insertAccom(accomDTO);
    }

//    public void setAccomPolicy(RatePolicyDTO rateDTO){
//        ratePolicyDAO.setAccomPolicy(rateDTO);
//    }

    public void setAccomDaily(DailyRateDTO dailyDTO) {

    }

//    public void setAccomDiscountPolicy()

    public List<AmenityDTO> getAmenityList(AccommodationDTO accomDTO){
        return amenityDAO.getAmenity(accomDTO.getAccomId());
    }

    public RatePolicyDTO getRate(AccommodationDTO accomDTO){
        return ratePolicyDAO.getRate(accomDTO.getAccomId());
    }

    public List<ReviewDTO> getReviews(AccommodationDTO accomDTO){
        return reviewDAO.selectReviews(accomDTO.getAccomId());
    }
    
    public void updateAccomStatus(int id, AccommodationStatus status)
    {
        accomDAO.updateAccomStatus(id, status);
    }


}
