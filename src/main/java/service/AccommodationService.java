package service;

import Container.IocContainer;
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
    private DailyRateDAO dailyRateDAO;
    private ReviewDAO reviewDAO;
    public AccommodationService(AccommodationDAO accomDAO, AmenityDAO amenityDAO,
                                ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, ReviewDAO reviewDAO){
        this.accomDAO = accomDAO;
        this.amenityDAO = amenityDAO;
        this.reservationDAO = reservationDAO;
        this.ratePolicyDAO = ratePolicyDAO;
        this.reviewDAO = reviewDAO;
    }

    public AccommodationService(IocContainer iocContainer){
        this.accomDAO = iocContainer.accommodationDAO();
        this.amenityDAO = iocContainer.amenityDAO();
        this.reservationDAO = iocContainer.reservationDAO();
        this.ratePolicyDAO = iocContainer.ratePolicyDAO();
        this.reviewDAO = iocContainer.reviewDAO();
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
    public void setAccomPolicy(RatePolicyDTO rateDTO){
        ratePolicyDAO.setAccomPolicy(rateDTO);
    }

    public void setAccomDaily(DailyRateDTO dailyDTO) {
        dailyRateDAO.setAccomDaily(dailyDTO);
    }

//    public void setAccomDiscountPolicy()

    public List<AmenityDTO> getAmenityList(AccommodationDTO accomDTO){
        return amenityDAO.selectAmenityByAccomID(accomDTO.getAccomId());
    }



    public RatePolicyDTO getRate(AccommodationDTO accomDTO){
        return ratePolicyDAO.getRate(accomDTO.getAccomId());
    }
    public DailyRateDTO getDaily(int accomID) {
        return DailyRateDAO.getDaily(accomID);
    }
    public List<ReviewDTO> getReviews(AccommodationDTO accomDTO){
        return reviewDAO.selectReviews(accomDTO.getAccomId());
    }
    
    public void updateAccomStatus(int id, AccommodationStatus status)
    {
        accomDAO.updateAccomStatus(id, status);
    }
    


}
