package network.Client.Handler;

import Container.IocContainer;
import lombok.Getter;
import lombok.Setter;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import view.AccommodationView;
import view.UserView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

@Setter
@Getter
public abstract class ActorHandler {

	protected UserView userView;
	protected AccommodationView accomView;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	protected UserDTO currentUser;

	protected static final Scanner sc = new Scanner(System.in);

	public ActorHandler(IocContainer iocContainer, ObjectOutputStream oos, ObjectInputStream ois) {
		userView = iocContainer.userView();
		accomView = iocContainer.accommodationView();
		this.oos = oos;
		this.ois = ois;
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
