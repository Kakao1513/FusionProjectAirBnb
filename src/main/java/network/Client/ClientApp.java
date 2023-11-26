package network.Client;

import Container.IocContainer;
import Container.ViewContainer;
import network.Server.Server;
import view.AccommodationView;
import view.UserView;
import view.View;

public class ClientApp {
	public static void main(String[] args){

		Client client = new Client("localhost", Server.SERVER_PORT, ViewContainer.getInstance());
		client.run();
	}
}
