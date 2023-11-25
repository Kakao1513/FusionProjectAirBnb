package network.Client.Handler;

import Container.IocContainer;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import view.AccommodationView;
import view.UserView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public abstract class ClientHandler {

	protected UserView userView;
	protected AccommodationView accomView;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	protected UserDTO currentUser;

	protected static final Scanner sc = new Scanner(System.in);

	public ClientHandler(IocContainer iocContainer, ObjectOutputStream oos, ObjectInputStream ois, UserDTO currentUser) {
		userView = iocContainer.userView();
		accomView = iocContainer.accommodationView();
		this.oos = oos;
		this.ois = ois;
		this.currentUser = currentUser;
	}

	abstract public void run();

	protected Response requestToServer(Request request) {
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
