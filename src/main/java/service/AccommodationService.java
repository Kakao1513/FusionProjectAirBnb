package service;

import persistence.dao.*;
import persistence.dto.*;


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

    public List<AmenityDTO> getAmenityList(int accomID){
        return amenityDAO.getAmenity(accomID);
    }

    public List<ReservationDTO> getReservationList(int accomID, String startDate, String endDate){
        return reservationDAO.getReservations(accomID, startDate, endDate);
    }

    public RatePolicyDTO getRate(int accomID){
        return ratePolicyDAO.getRate(accomID);
    }

    public List<ReviewDTO> getReviews(int accomID){
        return reviewDAO.selectReviews(accomID);
    }

}
