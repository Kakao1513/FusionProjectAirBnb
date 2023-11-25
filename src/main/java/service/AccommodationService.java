package service;

import Container.IocContainer;
import Enums.AccommodationStatus;
import persistence.dao.*;
import persistence.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccommodationService {
	private final AccommodationDAO accomDAO;
	private final AmenityDAO amenityDAO;
	private final ReservationDAO reservationDAO;
	private final RatePolicyDAO ratePolicyDAO;
	private final DailyRateDAO dailyRateDAO;
	private final ReviewDAO reviewDAO;

	public AccommodationService(IocContainer iocContainer) {
		this.accomDAO = iocContainer.accommodationDAO();
		this.amenityDAO = iocContainer.amenityDAO();
		this.reservationDAO = iocContainer.reservationDAO();
		this.ratePolicyDAO = iocContainer.ratePolicyDAO();
		this.reviewDAO = iocContainer.reviewDAO();
		this.dailyRateDAO = iocContainer.dailyRateDAO();

	}
	public List<AccommodationDTO> selectAccom(Map<String, Object> filters) {
		return accomDAO.selectAccom(filters);
	}

	public List<AccommodationDTO> selectAccom(String status) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("status", status);
		return accomDAO.selectAccom(filters);
	}

	public List<AccommodationDTO> selectAccomByUserID(int userID) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("userID", userID);
		return accomDAO.selectAccom(filters);
	}


	public AccommodationDTO getAccom(int accomID) {
		return accomDAO.getAccom(accomID);
	}

	public void insertAccom(AccommodationDTO accomDTO) {
		accomDAO.insertAccom(accomDTO);
	}

	public void setAccomPolicy(RatePolicyDTO rateDTO) {
		ratePolicyDAO.setAccomPolicy(rateDTO);
	}

	public void setAccomDaily(DailyRateDTO dailyDTO) {
		dailyRateDAO.setAccomDaily(dailyDTO);
	}

//    public void setAccomDiscountPolicy()

	public List<AmenityDTO> getAmenityList(AccommodationDTO accomDTO) {
		return amenityDAO.selectAmenityByAccomID(accomDTO.getAccomId());
	}

	// 숙소 객체를 입력받아 숙소의 기본 요금을 담은 객체를 반환하는 함수
	public RatePolicyDTO getRate(AccommodationDTO accomDTO) {
		return ratePolicyDAO.getRate(accomDTO.getAccomId());
	}

	public DailyRateDTO getDaily(AccommodationDTO accomDTO) {
		return dailyRateDAO.getDaily(accomDTO.getAccomId());
	}

	public List<ReviewDTO> getReviews(AccommodationDTO accomDTO) {
		return reviewDAO.selectReviews(accomDTO.getAccomId());
	}

	public void updateAccomStatus(int id, String status) {
		accomDAO.updateAccomStatus(id, status);
	}


}
