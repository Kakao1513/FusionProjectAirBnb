package network.Client.Handler;

import Container.ViewContainer;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccommodationRegister;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.RatePolicyDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
				setAccomRatePolicy();
			}
			case 3 -> {

			}
			case 4 -> {

			}
			case 5 -> {

			}
			case 6 -> {

			}
			case 7 -> {
				getConfirmedAccomByUser();
			}
		}
	}

	private List<AccommodationDTO> getConfirmedAccomByUser() {
		Request request = Request.builder().payloadType(PayloadType.USER).roleType(RoleType.HOST).method(Method.GET).payload(currentUser).build();
		Response response = requestToServer(request);
		List<AccommodationDTO> myAccomList = null;
		if (response != null && response.getIsSuccess()) {
			myAccomList = (List<AccommodationDTO>) response.getPayload();
			System.out.println("=================================================나의 숙소 목록===============================================================");
			accomView.displayAccomListCountOrder(myAccomList);
		} else {
			System.out.println("요청이 실패하였습니다.");
		}
		return myAccomList;
	}

	private void setAccomRatePolicy() {
		List<AccommodationDTO> myAccomList = getConfirmedAccomByUser();
		int select = 0;
		while (true) {
			System.out.print("숙소 번호를 입력하세요:");
			select = Integer.parseInt(sc.nextLine());
			if (0 < select && select <= myAccomList.size()) {
				break;
			} else {
				System.out.println("잘못된 입력입니다. 다시 입력해주세요.");
			}
		}
		AccommodationDTO selectedAccom = myAccomList.get(select - 1);
		RatePolicyDTO ratePolicyDTO = accomView.getRatePolicyFromUser(selectedAccom.getAccomID());
		Request request = Request.builder().method(Method.POST).payloadType(PayloadType.ACCOMMODATION).roleType(RoleType.HOST).payload(ratePolicyDTO).build();
		Response response = requestToServer(request);
		if (response != null && response.getIsSuccess()) {
			AccommodationDTO accom = (AccommodationDTO) response.getPayload();
			System.out.println(accom.getAccomName() + "숙소의 요금이 정상적으로 등록되었습니다.");
		} else {
			System.out.println("요청이 잘못되었습니다.");
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
