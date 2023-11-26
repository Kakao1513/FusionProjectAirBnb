package service;

import Container.IocContainer;
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
	private final DiscountPolicyDAO discountDAO;

	public AccommodationService(IocContainer iocContainer) {
		this.accomDAO = iocContainer.accommodationDAO();
		this.amenityDAO = iocContainer.amenityDAO();
		this.reservationDAO = iocContainer.reservationDAO();
		this.ratePolicyDAO = iocContainer.ratePolicyDAO();
		this.reviewDAO = iocContainer.reviewDAO();
		this.dailyRateDAO = iocContainer.dailyRateDAO();
		this.discountDAO = iocContainer.discountPolicyDAO();

	}

	// 1. 숙박 등록 신청(이름, 숙소 소개, 객실 타입(공간 전체/개인실), 수용 정보, 편의시설)
	public void insertAccom(AccommodationDTO accomDTO) {
		if (accomDAO.insertAccom(accomDTO) >= 1){
			accomDAO.insertRooms(accomDTO);
			RatePolicyDTO free = RatePolicyDTO
					.builder()
					.accomID(accomDTO.getAccomID())
					.weekday(0)
					.weekend(0)
					.build();

			ratePolicyDAO.setAccomPolicy(free);
		}

	}
	// 1.1 모든 편의시설 리스트를 반환
	public List<AmenityDTO> selectAmenityByCategory(String category){
		return amenityDAO.selectAmenityByCategory(category);
	}
	// 1.2 편의시설 리스트 삽입
	public void insertAccomAmenity(AccommodationDTO accomDTO, AmenityDTO amenityDTO){
		amenityDAO.insertAccomAmenity(accomDTO.getAccomID(), amenityDTO.getAmenityID());
	}

	// 2. 요금 정책 설정(주말요금/평일요금 일괄 설정)
	public void setAccomPolicy(RatePolicyDTO rateDTO) {
		ratePolicyDAO.setAccomPolicy(rateDTO);
	}

	// 3.1 할인 정책 설정(일별 요금 설정)
	public int setAccomDaily(DailyRateDTO dailyRateDTO) {
		return dailyRateDAO.setAccomDaily(dailyRateDTO);
	}

	// 3.2 할인 정책 설정(연박 할인 적용 기간 설정, 정량/정률 설정, 이전 예약 건에 대해서도 할인 요금 적용 여부 보이기)
	public int insertDiscount(DiscountPolicyDTO discountDTO){
		return discountDAO.insertDiscount(discountDTO);
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

	public List<AccommodationDTO> selectAccomByUser(UserDTO userDTO){
		Map<String, Object> filters = new HashMap<>();
		filters.put("userID", userDTO.getUserId());
		return accomDAO.selectAccom(filters);
	}

	public List<AccommodationDTO> selectConfirmedAccomByUser(UserDTO userDTO){
		Map<String, Object> filters = new HashMap<>();
		filters.put("userID", userDTO.getUserId());
		filters.put("status", "Confirmed");
		return accomDAO.selectAccom(filters);
	}

	// 12. 숙소 필터링(동적 쿼리로 동작해야 함, 동적쿼리를 보이기 위해 적절한 사전 데이터 준비)
	public List<AccommodationDTO> selectAccom(Map<String, Object> filters) {
		return accomDAO.selectAccom(filters);
	}

	// 13. 특정 숙소 선택 시 해당 숙소 상세 정보 보기
	// 13.1 숙소명, 소개
	public AccommodationDTO getAccom(int accomID) {
		return accomDAO.getAccom(accomID);
	}
	// 13.2 편의시설
	public List<AmenityDTO> getAmenityList(AccommodationDTO accomDTO) {
		return amenityDAO.selectAmenityByAccomID(accomDTO.getAccomID());
	}
	// 13.3 숙박 요금
	public RatePolicyDTO getRate(AccommodationDTO accomDTO) {
		return ratePolicyDAO.getRate(accomDTO.getAccomID());
	}
	public DailyRateDTO getDaily(AccommodationDTO accomDTO) {
		return dailyRateDAO.getDaily(accomDTO.getAccomID());
	}
	// 13.4 예약 가능 일자 -> ReservationService.getReservationList
	// 13.5 후기
	public List<ReviewDTO> getReviews(AccommodationDTO accomDTO) {
		return reviewDAO.selectReviews(accomDTO.getAccomID());
	}
	/////////////////////////////////////////////




}
