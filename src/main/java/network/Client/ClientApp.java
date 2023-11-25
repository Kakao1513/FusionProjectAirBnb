package network.Client;

import Container.IocContainer;
import network.Server.Server;
import view.AccommodationView;
import view.UserView;

public class ClientApp {
	public static void main(String[] args){

		Client client = new Client("localhost", Server.SERVER_PORT, IocContainer.iocContainer());
		client.run();
	}
}
