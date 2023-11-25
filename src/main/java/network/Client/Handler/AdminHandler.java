package network.Client.Handler;

import Container.IocContainer;
import Enums.AccommodationStatus;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Packet.AccomRecognizeInfo;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.UserDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class AdminHandler extends ActorHandler {

	public AdminHandler(IocContainer iocContainer, ObjectOutputStream oos, ObjectInputStream ois, UserDTO currentUser) {
		super(iocContainer, oos, ois, currentUser);
	}

	@Override
	public void run() {
		int jobOption = -1;
		while (jobOption != 0) {
			jobOption = userView.selectAdminJob();
			selectAdminJob(jobOption);
		}
	}

	private void selectAdminJob(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로");
			}
			case 1 -> { // TODO:숙소 등록 승인 거절
				viewReadyAccomList();
				accomRecognizeChange();
			}
			case 2 -> { //TODO : 숙소별 월별 예약 현황확인

			}
			case 3 -> { //TODO : 숙소별 월별 총매출 확인

			}
		}
	}

	private void viewReadyAccomList() {
		Request request = Request.builder().roleType(RoleType.ADMIN).payloadType(PayloadType.ACCOMMODATION).method(Method.GET).build();
		Response response = requestToServer(request);
		if (response.getIsSuccess()) {
			List<AccommodationDTO> readyAccomList = (List<AccommodationDTO>) response.getPayload();
			System.out.println("=============승인 대기 중인 숙소 목록===============");
			accomView.displayAccomList(readyAccomList);
		}
	}

	private void accomRecognizeChange() {
		System.out.print("숙소번호를 선택하세요. : ");
		int accomID = Integer.parseInt(sc.nextLine());
		System.out.println("1.승인 2.거절 : ");
		int statusNum = Integer.parseInt(sc.nextLine());
		AccommodationStatus status = switch (statusNum) {
			case 1 -> AccommodationStatus.Confirmed;
			case 2 -> AccommodationStatus.Refused;
			default -> throw new IllegalArgumentException();
		};
		Request request = Request.builder().roleType(RoleType.ADMIN).method(Method.PUT).payloadType(PayloadType.USER).build();
		AccomRecognizeInfo recognizeInfo = new AccomRecognizeInfo(accomID, status);
		request.setPayload(recognizeInfo);
		Response response = requestToServer(request);
		if(response.getIsSuccess()){
			System.out.println("갱신이 완료되었습니다.");
		}
	}


}
