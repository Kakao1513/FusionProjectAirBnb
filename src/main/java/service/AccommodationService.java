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
    private ReservationDAO reservationDAO;
    private RatePolicyDAO ratePolicyDAO;
    private ReviewDAO reviewDAO;
    public AccommodationService(AccommodationDAO accomDAO, AmenityDAO amenityDAO,
                                ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, ReviewDAO reviewDAO){
        this.accomDAO = accomDAO;
        this.amenityDAO = amenityDAO;
        this.reservationDAO = reservationDAO;
        this.ratePolicyDAO = ratePolicyDAO;
        this.reviewDAO = reviewDAO;
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

    public List<ReservationDTO> getReservationList(AccommodationDTO accomDTO, LocalDate date){
        return reservationDAO.getReservations(accomDTO.getAccomId(), date);
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
    
    public List<ReservationDTO> checkReservationStatus(int id, int month)
    {
        LocalDate date = LocalDate.now().withMonth(month);
        List<ReservationDTO> reservationList;
        reservationList = reservationDAO.getReservations(id, date);
        
        return reservationList;
    }

}
