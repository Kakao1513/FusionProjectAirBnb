package network.Client;

import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccomMoreInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.UserDTO;
import view.AccommodationView;
import view.UserView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
	private final Scanner sc = new Scanner(System.in);
	private final String ip;
	private final int port;
	private UserView userView;
	private AccommodationView accomView;
	private Socket socket;

	private UserDTO currentUser;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	public Client(String ip, int port, UserView view, AccommodationView accomView) {
		this.ip = ip;
		this.port = port;
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		userView = view;
		this.accomView = accomView;
	}

	private boolean login() {
		boolean isSuccess = false;
		UserDTO loginInfo = userView.loginRequestView();
		Request loginRequest = new Request();
		loginRequest.setRoleType(RoleType.COMMON);
		loginRequest.setMethod(Method.POST);
		loginRequest.setPayloadType(PayloadType.USER);
		loginRequest.setPayload(loginInfo);
		Response response;
		try {
			oos.writeObject(loginRequest);
			oos.flush();
			response = (Response) ois.readObject();
			if (response.getIsSuccess()) {
				currentUser = (UserDTO) response.getPayload();
				System.out.println("로그인 성공");
				isSuccess = true;
			} else {
				System.out.println(response.getErrorMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void run() {
		try {
			while (!login()) ;
			int select = 1;
			while (select != 0) {
				int jobOption = -1;
				select = userView.viewJobs(currentUser);
				switch (select) {
					case 0 -> {
						System.out.println("로그아웃.");
					}
					case 1 -> {
						while (jobOption != 0) {
							jobOption = userView.selectGuestJob();
							selectGuestJob(jobOption);
						}
					}
					case 2 -> {
						while (jobOption != 0) {
							jobOption = userView.selectHostJob();
							selectHostJob(jobOption);
						}
					}
					case 3 -> {
						while (jobOption != 0) {
							jobOption = userView.selectAdminJob();
							selectAdminJob(jobOption);
						}
					}
				}
			}
		} finally {
			closeResource();
		}
	}

	private void selectAdminJob(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로");
			}
			case 1 -> { // TODO:숙소 등록 승인 거절

			}
			case 2 -> { //TODO : 숙소별 월별 예약 현황확인

			}
			case 3 -> { //TODO : 숙소별 월별 총매출 확인

			}
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
					myPageJobHandle(jobOption);
				}
			}
			case 2 -> { //숙소 목록 보기
				viewAccomList();
			}
			case 3 -> { //숙소 필터링
				accomFiltering();
			}
			case 4 -> { //숙소 예약 신청

			}
			case 5 -> { //숙소 상세 정보 보기
				viewAccomMoreInfo();
			}
		}
	}

	public void viewAccomMoreInfo() {
		Integer accomID = accomView.getAccomNumberFromUser();
		LocalDate date = accomView.getReservationDate();
		Object[] payload = {accomID, date};
		Response response = communicationToServer(new Request(Method.GET, PayloadType.ACCOMMODATION, RoleType.COMMON, payload));

		if(response.getIsSuccess()){
			AccomMoreInfo moreInfo = (AccomMoreInfo) response.getPayload();
			AccommodationDTO curAccom =moreInfo.getCurAccom();
			accomView.displayAccomInfo(curAccom, moreInfo.getAccomRate());
			accomView.displayAmenity(moreInfo.getAmenityList());
			accomView.displayReviews(moreInfo.getReviewList());
			accomView.displayReservationCalendar(date, curAccom.getCapacity(), moreInfo.getReservationList());
		}

	}

	public void selectHostJob(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로.");
			}
			case 1 -> {

			}
			case 2 -> {
//				setAccomPolicy();
			}
			case 3 -> {

			}
			case 4 -> {

			}
			case 5 -> {

			}
			case 6 -> {

			}
		}
	}

	public void viewAccomList() {
		List<AccommodationDTO> acList = null;
		Request req = new Request();
		req.setMethod(Method.GET);
		req.setRoleType(RoleType.GUEST);
		req.setPayloadType(PayloadType.ACCOMMODATION);
		Response response = communicationToServer(req);
		if (response.getIsSuccess()) {
			acList = (List<AccommodationDTO>) response.getPayload();
		}
		accomView.displayAccomList(acList);
	}


	public void myPageJobHandle(int select) {
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
		Response response = communicationToServer(req);

		if (response.getIsSuccess()) {
			System.out.println("개인정보 수정 성공.");
			currentUser = (UserDTO) response.getPayload();
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

		Response response = communicationToServer(request);
		if (response.getIsSuccess()) {
			List<AccommodationDTO> accommodationDTOS = (List<AccommodationDTO>) response.getPayload();
			accomView.displayAccomList(accommodationDTOS);
		}
	}


	private void closeResource() {
		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Response communicationToServer(Request request) {
		Response res = null;
		try {
			oos.writeObject(request);
			oos.flush();
			res = (Response) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}
}
