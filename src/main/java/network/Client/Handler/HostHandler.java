package network.Client.Handler;

import Container.ViewContainer;
import network.Client.ActorHandler;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccommodationRegister;
import network.Protocol.Packet.ReservationInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class HostHandler extends ActorHandler {
	public HostHandler(ViewContainer viewContainer, ObjectOutputStream oos, ObjectInputStream ois) {
		super(viewContainer, oos, ois);
	}

	@Override
	public void run() {
		int jobOption = -1;
		while (jobOption != 0) {
			jobOption = userView.selectHostJob();
			selectHostJob(jobOption);
		}
	}


	private void selectHostJob(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로.");
			}
			case 1 -> {
				registerAccomodation();
			}
			case 2 -> {
				registAccomRatePolicy();
			}
			case 3 -> {
				registDiscountPolicy();
			}
			case 4 -> {
				showReservationByAccom();
			}
			case 5 -> {
				reservationConfirmOrRefuse();
			}
			case 6 -> {
				//답글등록
			}
			case 7 -> {
				selectAccomByUser();
			}
		}
	}

	private void reservationConfirmOrRefuse() {
		List<AccommodationDTO> myAccomList = selectConfirmedAccomByUser();
		if (!myAccomList.isEmpty()) {
			int select = accomView.readAccomIndex(myAccomList);
			AccommodationDTO selectedAccom = myAccomList.get(select);
			ReservationInfo reservationInfo = getReservationListByAccomodation(selectedAccom, null);
			List<ReservationDTO> reservationDTOS = reservationInfo.getReservationDTOS();
			List<UserDTO> userNames = reservationInfo.getUserDTOS();
			List<AccommodationDTO> accomNames = reservationInfo.getAccommodationDTOS();
			if (!reservationDTOS.isEmpty()) {
				reservationView.displayReservations(reservationDTOS, accomNames, userNames);
				int reserveSelect = reservationView.readReservationIndex(reservationDTOS);
				ReservationDTO selectReserve = reservationDTOS.get(reserveSelect);
				System.out.print("1.예약 승인 2.예약 거절 : ");
				int isConfirm = Integer.parseInt(sc.nextLine());
				String status = switch (isConfirm) {
					case 1 -> "예약중";
					case 2 -> "예약반려됨";
					default -> throw new IllegalArgumentException();
				};
				selectReserve.setReservationInfo(status);
				Request request = Request.builder().payloadType(PayloadType.RESERVATION).roleType(RoleType.HOST).method(Method.PUT).payload(selectReserve).build();
				Response response = requestToServer(request);
				if (response != null && response.getIsSuccess()) {
					System.out.println(response.getMessage());
				}
			} else {
				System.out.println("예약 정보가 없습니다.");
			}
		} else {
			System.out.println("숙소 정보가 없습니다.");
		}

	}

	private ReservationInfo getReservationListByAccomodation(AccommodationDTO accommodationDTO, LocalDate nowMonth) {
		Object[] payload = {accommodationDTO, nowMonth};
		Request request = Request.builder().method(Method.GET).roleType(RoleType.HOST).payloadType(PayloadType.RESERVATION).payload(payload).build();
		List<ReservationDTO> reservationDTOs = null;
		ReservationInfo reservationInfo = null;
		Response response = requestToServer(request);
		if (response != null && response.getIsSuccess()) {
			reservationInfo = (ReservationInfo) response.getPayload();
		}
		return reservationInfo;
	}

	private void showReservationByAccom() {
		List<AccommodationDTO> myAccomList = selectConfirmedAccomByUser();
		if (!myAccomList.isEmpty()) {
			int select = accomView.readAccomIndex(myAccomList);
			AccommodationDTO selectAccom = myAccomList.get(select);
			YearMonth yearMonth = accomView.readYearMonth();
			LocalDate nowMonth = yearMonth.atDay(1);
			ReservationInfo reservationInfo = getReservationListByAccomodation(selectAccom, nowMonth);
			List<ReservationDTO> reservationDTOs = reservationInfo.getReservationDTOS();
			System.out.println("===================" + selectAccom.getAccomName() + "숙소의 예약 현황" + "========================");
			reservationView.displayReservationCalendar(nowMonth, selectAccom.getCapacity(), reservationDTOs);
		} else {
			System.out.println("숙소 정보가 없습니다.");
		}
	}

	private List<AccommodationDTO> selectConfirmedAccomByUser() { //승인된 숙소목록만 보여줌
		Request request = Request.builder().payloadType(PayloadType.USER).roleType(RoleType.HOST).method(Method.GET).payload(currentUser).build();
		Response response = requestToServer(request);
		List<AccommodationDTO> myAccomList = null;
		if (response != null && response.getIsSuccess()) {
			myAccomList = (List<AccommodationDTO>) response.getPayload();
			accomView.displayAccomListCountOrder(myAccomList);
		} else {
			System.out.println("요청이 실패하였습니다.");
		}
		return myAccomList;
	}

	private List<AccommodationDTO> selectAccomByUser() { //승인되지 않은 목록까지 보여줌
		Request request = Request.builder().payloadType(PayloadType.ACCOMMODATION).roleType(RoleType.HOST).method(Method.GET).payload(currentUser).build();
		Response response = requestToServer(request);
		List<AccommodationDTO> myAccomList = null;
		if (response != null && response.getIsSuccess()) {
			myAccomList = (List<AccommodationDTO>) response.getPayload();
			accomView.displayAccomListCountOrder(myAccomList);
		} else {
			System.out.println("요청이 실패하였습니다.");
		}
		return myAccomList;
	}

	private void registDiscountPolicy() {
		Request request = Request.builder().method(Method.PUT).roleType(RoleType.HOST).payloadType(PayloadType.ACCOMMODATION).build();
		List<AccommodationDTO> myAccomList = selectConfirmedAccomByUser();
		if (!myAccomList.isEmpty()) {
			int select = accomView.readAccomIndex(myAccomList);
			DiscountPolicyDTO discountPolicyDTO = accomView.getDiscountFromUser(myAccomList.get(select));
			request.setPayload(discountPolicyDTO);
			Response response = requestToServer(request);
			if (response != null && response.getIsSuccess()) {
				AccommodationDTO accom = (AccommodationDTO) response.getPayload();
				System.out.println(accom.getAccomName() + " 숙소의 할인 정책을 정상적으로 등록하였습니다.");
			} else {
				System.out.println("요청이 잘못되었습니다.");
			}
		} else {
			System.out.println("숙소 정보가 없습니다.");
		}

	}

	private void registAccomRatePolicy() {
		List<AccommodationDTO> myAccomList = selectConfirmedAccomByUser();
		if (!myAccomList.isEmpty()) {
			int select = accomView.readAccomIndex(myAccomList);
			AccommodationDTO selectedAccom = myAccomList.get(select);
			RatePolicyDTO ratePolicyDTO = accomView.getRatePolicyFromUser(selectedAccom.getAccomID());
			Request request = Request.builder().method(Method.POST).payloadType(PayloadType.ACCOMMODATION).roleType(RoleType.HOST).payload(ratePolicyDTO).build();
			Response response = requestToServer(request);
			if (response != null && response.getIsSuccess()) {
				AccommodationDTO accom = (AccommodationDTO) response.getPayload();
				System.out.println(accom.getAccomName() + " 숙소의 요금이 정상적으로 등록되었습니다.");
			} else {
				System.out.println("요청이 잘못되었습니다.");
			}
		} else {
			System.out.println("숙소 정보가 없습니다.");
		}

	}

	private void registerAccomodation() {
		AccommodationDTO registerAccom = accomView.getAccomInfoFromUser(currentUser);
		Boolean[] basicAmenities = amenityView.registBasicAmenities();
		Boolean[] accessibilityAmenities = amenityView.registAccessibilityAmenities();
		Boolean[] safetyAmenites = amenityView.registSafetyAmenities();
		Boolean[] favoriteAmenites = amenityView.registFavoriteAmenities();
		List<Boolean[]> amenityList = new ArrayList<>();
		amenityList.add(basicAmenities);
		amenityList.add(accessibilityAmenities);
		amenityList.add(safetyAmenites);
		amenityList.add(favoriteAmenites);

		AccommodationRegister accommodationRegister = new AccommodationRegister(amenityList, registerAccom);
		Request req = Request.builder().payloadType(PayloadType.USER).method(Method.POST).roleType(RoleType.HOST).build();
		req.setPayload(accommodationRegister);
		Response response = requestToServer(req);
		if (response.getIsSuccess()) {
			System.out.println("숙소등록을 정상적으로 신청하였습니다.");
		}
	}

}
