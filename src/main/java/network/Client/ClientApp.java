package network.Client;

import network.Server.Server;
import view.UserView;

public class ClientApp {
	public static void main(String[] args){

		Client client = new Client("172.30.85.156", Server.SERVER_PORT, new UserView());
		client.run();
	}
}
