package network.Client.Handler;

import Container.IocContainer;
import persistence.dto.UserDTO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class HostHandler extends ActorHandler {
	public HostHandler(IocContainer iocContainer, ObjectOutputStream oos, ObjectInputStream ois, UserDTO currentUser) {
		super(iocContainer, oos, ois, currentUser);
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

}
