package network.Client.Handler;

import Container.IocContainer;
import Container.ViewContainer;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccomMoreInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.UserDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Date;
import java.time.LocalDate;
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
				viewAccomList();
			}
			case 3 -> { //숙소 필터링
				accomFiltering();
			}
			case 4 -> { //숙소 예약 신청
				requestReservation();
			}
			case 5 -> { //숙소 상세 정보 보기
				viewAccomMoreInfo();
			}
		}
	}

	private void requestReservation() {
		Request request = Request.builder().roleType(RoleType.GUEST).method(Method.POST).payloadType(PayloadType.RESERVATION).build();
		System.out.println("예약할 숙소 번호를 선택하세요.");

	}

	public void myPageHandle(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로 돌아갑니다.");
			}
			case 1 -> {

			}
			case 2 -> {

			}
			case 3 -> {

			}
			case 4 -> {
				changePrivacy();
			}
		}
	}

	private void accomFiltering() {
		Map<String, Object> filters = new HashMap<>(); //Request의 Payload에 담겨서 온다.
		int order = 0;
		while (order != 5) {
			order = accomView.displayFilterList(); // Client로 가야됨.
			switch (order) {
				case 1 -> filters.put("accomName", accomView.getAccomNameFromUser());
				case 2 -> filters.put("period", accomView.getPeriodFromUser());
				case 3 -> filters.put("capacity", accomView.getCapacityFromUser());
				case 4 -> filters.put("accomType", accomView.getAccomTypeFromUser());
			}
		}
		accomView.displayAppliedFilters(filters);
		Request request = new Request();
		request.setMethod(Method.PUT);
		request.setPayloadType(PayloadType.ACCOMMODATION);
		request.setRoleType(RoleType.GUEST);
		request.setPayload(filters);

		Response response = requestToServer(request);
		if (response.getIsSuccess()) {
			List<AccommodationDTO> accommodationDTOS = (List<AccommodationDTO>) response.getPayload();
			accomView.displayAccomList(accommodationDTOS);
		}
	}
	public void viewAccomList() {
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
	}

	public void viewAccomMoreInfo() {
		Integer accomID = accomView.getAccomNumberFromUser();
		LocalDate date = accomView.getReservationDate();
		Object[] payload = {accomID, date};
		Response response = requestToServer(new Request(Method.GET, PayloadType.ACCOMMODATION, RoleType.COMMON, payload));

		if (response.getIsSuccess()) {
			AccomMoreInfo moreInfo = (AccomMoreInfo) response.getPayload();
			AccommodationDTO curAccom = moreInfo.getCurAccom();
			accomView.displayAccomInfo(curAccom, moreInfo.getAccomRate());
			accomView.displayAmenity(moreInfo.getAmenityList());
			accomView.displayReviews(moreInfo.getReviewList());
			accomView.displayReservationCalendar(date, curAccom.getCapacity(), moreInfo.getReservationList());
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
