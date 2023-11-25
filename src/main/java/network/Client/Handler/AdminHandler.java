package network.Client.Handler;

import Container.IocContainer;
import persistence.dto.UserDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class AdminHandler extends ClientHandler {

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

			}
			case 2 -> { //TODO : 숙소별 월별 예약 현황확인

			}
			case 3 -> { //TODO : 숙소별 월별 총매출 확인

			}
		}
	}

}
