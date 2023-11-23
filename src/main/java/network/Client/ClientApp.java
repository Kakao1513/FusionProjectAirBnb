package network.Client;

import network.Server.Server;
import view.UserView;

public class ClientApp {
	public static void main(String[] args){

		Client client = new Client("192.168.0.10", Server.SERVER_PORT, new UserView());
		client.run();
	}
}
