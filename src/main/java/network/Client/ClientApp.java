package network.Client;

import network.Server.Server;

public class ClientApp {
	public static void main(String[] args){
		Client client = new Client("127.0.0.1", Server.SERVER_PORT);
		client.run();
	}
}
