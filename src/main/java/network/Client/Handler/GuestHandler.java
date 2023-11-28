package network.Client.Handler;

import Container.ViewContainer;
import network.Client.ActorHandler;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccomMoreInfo;
import network.Protocol.Packet.ReservationInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.ReviewDTO;
import persistence.dto.UserDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GuestHandler extends ActorHandler {
	public GuestHandler(ViewContainer viewContainer, ObjectOutputStream oos, ObjectInputStream ois) {
		super(viewContainer, oos, ois);
	}

	@Override
	public void run() {
		int jobOption = -1;
		while (jobOption != 0) {
			jobOption = userView.selectGuestJob();
			selectGuestJob(jobOption);
		}
	}

	public void selectGuestJob(int select) {

		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로.");
			}
			case 1 -> { //마이페이지 보기
				int jobOption = -1;
				while (jobOption != 0) {
					jobOption = userView.selectMyPageJobs(currentUser);
					myPageHandle(jobOption);
				}
			}
			case 2 -> { //숙소 목록 보기
				selectAccomList();
			}
			case 3 -> { //숙소 예약 신청
				requestReservation();
			}
		}
	}

	public void myPageHandle(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로 돌아갑니다.");
			}
			case 1 -> {
				ReservationInfo reservationInfo = getMyReservationList();
				List<ReservationDTO> reservationDTOList = reservationInfo.getReservationDTOS();
				List<UserDTO> userNames = reservationInfo.getUserDTOS();
				List<AccommodationDTO> accomNames = reservationInfo.getAccommodationDTOS();
				reservationView.displayReservations(reservationDTOList, accomNames, userNames);
			}
			case 2 -> {
				//예약 취소
				cancelReservation();
			}
			case 3 -> {
				//리뷰 등록
				registReview();
			}
			case 4 -> {
				changePrivacy();
			}
		}
	}

	private void registReview() {
		ReservationInfo reservationInfo = getMyReservationList();
		List<AccommodationDTO> reserveAccom = reservationInfo.getAccommodationDTOS();
		List<ReservationDTO> reservationDTOList = reservationInfo.getReservationDTOS();
		List<String> accomNames = new ArrayList<>();
		List<ReservationDTO> reviewableReservations = new ArrayList<>();
		for (int i = 0; i < reservationDTOList.size(); i++) {
			ReservationDTO reservationDTO = reservationDTOList.get(i);
			if(reservationDTO.getReservationInfo().equals("숙박 완료")){
				accomNames.add(reserveAccom.get(i).getAccomName());
				reviewableReservations.add(reservationDTO);
			}
		}
		if (reviewableReservations.isEmpty()) {
			System.out.println("리뷰를 등록할 수 있는 예약이 없습니다.");
			return;
		}

		int select = reservationView.selectReserveAccom(reviewableReservations, accomNames);
		ReservationDTO selectedReservation = reviewableReservations.get(select);
		ReviewDTO reviewDTO = reviewView.getReviewFromUser(currentUser, selectedReservation);
		Request request = Request.builder().roleType(RoleType.GUEST).method(Method.POST).payloadType(PayloadType.REVIEW).payload(reviewDTO).build();
		Response response = requestToServer(request);
		if (response != null && response.getIsSuccess()) {
			System.out.println("리뷰 등록이 완료되었습니다.");
		} else {
			System.out.println(response.getMessage());
		}
		//reviewView.getReviewFromUser(currentUser, reviewableReservations);
	}

	private void cancelReservation() { //TODO : 해야됨
		Request request = Request.builder().roleType(RoleType.GUEST).method(Method.DELETE).payloadType(PayloadType.RESERVATION).build();
		ReservationInfo reservationInfo = getMyReservationList();
		List<ReservationDTO> reservationDTOList = reservationInfo.getReservationDTOS();
		List<UserDTO> userNames = reservationInfo.getUserDTOS();
		List<AccommodationDTO> accomNames = reservationInfo.getAccommodationDTOS();
		reservationView.displayReadyReservations(reservationDTOList, accomNames, userNames);
		int select = reservationView.readReservationIndex(reservationDTOList);
		ReservationDTO selectedReservation = reservationDTOList.get(select);
		request.setPayload(selectedReservation);
		Response response = requestToServer(request);
		if (response.getIsSuccess()) {
			System.out.println("예약이 취소되었습니다.");
		} else {
			System.out.println("예약 취소가 실패하였습니다. 사유:" + response.getMessage());
		}
	}

	private void requestReservation() {
		Request request = Request.builder().roleType(RoleType.GUEST).method(Method.POST).payloadType(PayloadType.RESERVATION).build();

		ReservationDTO reservationDTO = new ReservationDTO();
		Object[] accomAndFilters = accomFiltering();

		List<AccommodationDTO> accommodationDTOS = (List<AccommodationDTO>) accomAndFilters[0];
		Map<String, Object> filters = (Map<String, Object>) accomAndFilters[1];

		int accomIndex = accomView.readAccomIndex(accommodationDTOS); //번호로 숙소를 선택
		if (accomIndex == -1) {
			System.out.println("숙소 선택을 취소합니다.");
			return;
		}
		AccommodationDTO selectedAccom = accommodationDTOS.get(accomIndex);
		viewAccomMoreInfo(selectedAccom);
		//후기 표시도 들어가야됨
		System.out.println("1.예약 2.뒤로가기.");
		int select = Integer.parseInt(sc.nextLine());
		if (select == 2) {
			return;
		}
		reservationDTO.setHeadcount((Integer) filters.get("headcount"));
		reservationDTO.setReserveDate(LocalDateTime.now());
		reservationDTO.setReservationInfo("승인대기중");
		reservationDTO.setAccommodationID(selectedAccom.getAccomID());
		reservationDTO.setUserID(currentUser.getUserId());
		reservationDTO.setCheckIn((LocalDate) filters.get("checkIn"));
		reservationDTO.setCheckOut((LocalDate) filters.get("checkOut"));
		request.setPayload(reservationDTO);
		Response response = requestToServer(request);
		if (response.getIsSuccess()) {
			System.out.println(response.getMessage());
			ReservationDTO resRserveDTO = (ReservationDTO) response.getPayload();
			reservationView.displayReservationInfo(resRserveDTO, selectedAccom.getAccomName(), currentUser.getName());
		} else {
			System.out.println("예약이 실패하였습니다. 사유:" + response.getMessage());
		}
	}

	private ReservationInfo getMyReservationList() {
		Request request = new Request();
		request.setMethod(Method.GET);
		request.setPayloadType(PayloadType.RESERVATION);
		request.setRoleType(RoleType.GUEST);
		request.setPayload(currentUser);
		Response response = requestToServer(request);
		ReservationInfo reservationInfo = null;
		if (response.getIsSuccess()) {
			reservationInfo = (ReservationInfo) response.getPayload();
		}
		return reservationInfo;
	}

	private Object[] accomFiltering() {
		Map<String, Object> filters = new HashMap<>(); //Request의 Payload에 담겨서 온다.
		int order = 0;
		//선택 조건
		while (order != 3) {
			order = accomView.displayFilterList(); // Client로 가야됨.
			switch (order) {
				case 1 -> filters.put("accomName", accomView.getAccomNameFromUser());
				case 2 -> filters.put("type", accomView.getAccomTypeFromUser());
			}
		}
		//필수 조건
		filters.put("headcount", accomView.getHeadcount());
		LocalDate checkIn = reservationView.getCheckIn();
		LocalDate checkOut = reservationView.getCheckOut();
		filters.put("checkIn", checkIn);
		filters.put("checkOut", checkOut);

		accomView.displayAppliedFilters(filters);
		Request request = new Request();
		request.setMethod(Method.PUT);
		request.setPayloadType(PayloadType.ACCOMMODATION);
		request.setRoleType(RoleType.GUEST);
		request.setPayload(filters);

		Response response = requestToServer(request);
		List<AccommodationDTO> accommodationDTOS = null;
		if (response.getIsSuccess()) {
			accommodationDTOS = (List<AccommodationDTO>) response.getPayload();
			accomView.displayAccomList(accommodationDTOS);
		}
		return new Object[]{accommodationDTOS, filters};
	}

	public List<AccommodationDTO> selectAccomList() {
		List<AccommodationDTO> acList = null;
		Request req = new Request();
		req.setMethod(Method.GET);
		req.setRoleType(RoleType.GUEST);
		req.setPayloadType(PayloadType.ACCOMMODATION);
		Response response = requestToServer(req);
		if (response.getIsSuccess()) {
			acList = (List<AccommodationDTO>) response.getPayload();
		}
		accomView.displayAccomList(acList);
		return acList;
	}

	public void viewAccomMoreInfo(AccommodationDTO accommodationDTO) {
		System.out.println("========선택한 숙소의 상세 정보 내역입니다.========");
		int accomID = accommodationDTO.getAccomID();
		LocalDate date = reservationView.getReservationDate();
		Object[] payload = {accomID, date};
		Response response = requestToServer(new Request(Method.GET, PayloadType.ACCOMMODATION, RoleType.COMMON, payload));

		if (response.getIsSuccess()) {
			AccomMoreInfo moreInfo = (AccomMoreInfo) response.getPayload();
			AccommodationDTO curAccom = moreInfo.getCurAccom();
			accomView.displayAccomInfo(curAccom, moreInfo.getAccomRate());
			accomView.displayAmenity(moreInfo.getAmenityList());
			accomView.displayReviews(moreInfo.getReviewList());
			reservationView.displayReservationCalendar(date, curAccom.getCapacity(), moreInfo.getReservationList());
		}

	}

	public void changePrivacy() {
		String newName, newPhone;
		Date newBirth;
		System.out.print("이름 입력:");
		newName = sc.nextLine();
		System.out.print("생일 입력( format : 1999-01-01 ):");
		newBirth = Date.valueOf(sc.nextLine());
		System.out.print("전화번호 입력 ( format : 010-1234-5678) : ");
		newPhone = sc.nextLine();
		Request req = new Request();
		req.setMethod(Method.PUT);
		req.setRoleType(RoleType.GUEST);
		req.setPayloadType(PayloadType.USER);
		UserDTO userDTO = new UserDTO(currentUser);
		Object[] body = {userDTO, newName, newBirth, newPhone};
		req.setPayload(body);
		Response response = requestToServer(req);

		if (response.getIsSuccess()) {
			System.out.println("개인정보 수정 성공.");
			UserDTO chUser = (UserDTO) response.getPayload();
			currentUser.setAccountId(chUser.getAccountId());
			currentUser.setName(chUser.getName());
			currentUser.setPhone(chUser.getPhone());
			currentUser.setBirth(chUser.getBirth());
			currentUser.setType(chUser.getType());
			currentUser.setPassword(chUser.getPassword());
			currentUser.setUserId(chUser.getUserId());
		}
	}


}
