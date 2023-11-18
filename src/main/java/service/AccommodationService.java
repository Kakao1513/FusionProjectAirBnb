package service;

import persistence.dao.*;
import persistence.dto.*;


import java.util.List;

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
        return accomDAO.selectAccom(status, null, null, null, null, null);
    }

    public List<AccommodationDTO> selectAccom(String accomName,
                                              String startDate, String endDate,
                                              String capacity,
                                              String accomType){
        return accomDAO.selectAccom("승인됨", accomName, startDate, endDate, capacity, accomType);
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
