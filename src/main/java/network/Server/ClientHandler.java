package network.Server;

import Controller.MainController;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dao.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler implements Runnable {
	private Socket client;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private MainController mainController;


	ClientHandler(Socket soc, AccommodationDAO accommodationDAO, UserDAO userDAO, ReviewDAO reviewDAO, ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, AmenityDAO amenityDAO) throws IOException {
		client = soc;
		oos = new ObjectOutputStream(client.getOutputStream());
		System.out.println("Server OutputStream Is Open");
		ois = new ObjectInputStream(client.getInputStream());
		System.out.println("Server InputStream Is Open");
		mainController = new MainController(userDAO,accommodationDAO,amenityDAO,reservationDAO,ratePolicyDAO,reviewDAO);
	}

	@Override
	public void run() {
		System.out.println("Client Connection.");
		try {
			Request request = (Request) ois.readObject();
			Response response = mainController.handle(request);
			oos.writeObject(response);
			oos.flush();

		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}

		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (client != null) {
				client.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void login() {

	}
}