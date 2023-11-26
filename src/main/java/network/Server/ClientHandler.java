package network.Server;

import Container.IocContainer;
import Controller.MainController;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dao.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

class ClientHandler implements Runnable {
	private final Socket client;
	private final ObjectOutputStream oos;
	private final ObjectInputStream ois;
	private final MainController mainController;

	public ClientHandler(Socket socket, IocContainer iocContainer) throws IOException{
		client = socket;
		oos = new ObjectOutputStream(client.getOutputStream());
		System.out.println("Server OutputStream Is Open");
		ois = new ObjectInputStream(client.getInputStream());
		System.out.println("Server InputStream Is Open");
		mainController = new MainController(iocContainer);

	}

	@Override
	public void run() {
		System.out.println("Client Connection.");
		try {
			while (!client.isClosed()) {
				Request request = (Request) ois.readObject();
				Response response = mainController.handle(request);
				oos.writeObject(response);
				oos.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resourceCloser();
		}
	}

	private void resourceCloser(){
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
}