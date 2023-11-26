package network.Client;

import Container.IocContainer;
import Container.ViewContainer;
import lombok.Getter;
import lombok.Setter;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import view.AccommodationView;
import view.AmenityView;
import view.UserView;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

@Setter
@Getter
public abstract class ActorHandler {

	protected UserView userView;
	protected AccommodationView accomView;
	protected AmenityView amenityView;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	protected static UserDTO currentUser;

	protected static final Scanner sc = new Scanner(System.in);

	public ActorHandler(ViewContainer viewContainer, ObjectOutputStream oos, ObjectInputStream ois) {
		userView = viewContainer.userView();
		accomView = viewContainer.accommodationView();
		amenityView = viewContainer.amenityView();
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
	protected static void setCurrentUser(UserDTO userDTO){
		currentUser = userDTO;
	}
}
