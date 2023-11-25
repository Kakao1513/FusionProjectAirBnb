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

	// 1. 숙박 등록 신청(이름, 숙소 소개, 객실 타입(공간 전체/개인실), 수용 정보, 편의시설)
	public void insertAccom(AccommodationDTO accomDTO) {
		accomDAO.insertAccom(accomDTO);
	}

	// 2. 요금 정책 설정(주말요금/평일요금 일괄 설정)
	public void setAccomPolicy(RatePolicyDTO rateDTO) {
		ratePolicyDAO.setAccomPolicy(rateDTO);
	}

	// 3. 할인 정책 설정(연박 할인 적용 기간 설정, 정량/정률 설정, 이전 예약 건에 대해서도 할인 요금 적용 여부 보이기)
	public void setAccomDaily(DailyRateDTO dailyDTO) {
		dailyRateDAO.setAccomDaily(dailyDTO);
	}

	// 4.1 숙박 예약 현황 보기(달력 화면 구성으로)
	// 유저 ID로 숙소 리스트를 받은 뒤 ReservationService의
	// getReservationList 함수를 이용하여 숙박 예약 현황 확인
	public List<AccommodationDTO> selectAccomByUserID(int userID) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("userID", userID);
		return accomDAO.selectAccom(filters);
	}

	// 7. 호스트의 숙소 등록 신청 승인/거절
	public void updateAccomStatus(int id, String status) {
		accomDAO.updateAccomStatus(id, status);
	}

	// 11. 숙소 목록 보기(전체 목록 조회)
	public List<AccommodationDTO> selectAccom(String status) {
		Map<String, Object> filters = new HashMap<>();
		filters.put("status", status);
		return accomDAO.selectAccom(filters);
	}

	// 12. 숙소 필터링(동적 쿼리로 동작해야 함, 동적쿼리를 보이기 위해 적절한 사전 데이터 준비)
	public List<AccommodationDTO> selectAccom(Map<String, Object> filters) {
		return accomDAO.selectAccom(filters);
	}


	// 13. 특정 숙소 선택 시 해당 숙소 상세 정보 보기
	public AccommodationDTO getAccom(int accomID) {
		return accomDAO.getAccom(accomID);
	}

	public List<AmenityDTO> getAmenityList(AccommodationDTO accomDTO) {
		return amenityDAO.selectAmenityByAccomID(accomDTO.getAccomId());
	}

	public RatePolicyDTO getRate(AccommodationDTO accomDTO) {
		return ratePolicyDAO.getRate(accomDTO.getAccomId());
	}

	public DailyRateDTO getDaily(AccommodationDTO accomDTO) {
		return dailyRateDAO.getDaily(accomDTO.getAccomId());
	}

	public List<ReviewDTO> getReviews(AccommodationDTO accomDTO) {
		return reviewDAO.selectReviews(accomDTO.getAccomId());
	}
	/////////////////////////////////////////////


}
