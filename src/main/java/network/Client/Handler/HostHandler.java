package network.Client.Handler;

import Container.IocContainer;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.AccommodationDTO;
import persistence.dto.UserDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HostHandler extends ActorHandler {
	public HostHandler(IocContainer iocContainer, ObjectOutputStream oos, ObjectInputStream ois) {
		super(iocContainer, oos, ois);
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

	private void registerAccomodation() {
		AccommodationDTO registerAccom = accomView.getAccomInfoFromUser(currentUser);
		Request req = Request.builder().payloadType(PayloadType.USER).method(Method.POST).roleType(RoleType.HOST).build();
		req.setPayload(registerAccom);
		Response response = requestToServer(req);
		if(response.getIsSuccess()){
			System.out.println("숙소등록을 정상적으로 신청하였습니다.");
		}
	}

}
