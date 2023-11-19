package network.Server;

import Controller.UserController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public final static int SERVER_PORT = 4000;
	private final ExecutorService exr;
	private final ServerSocket serverSocket;

	private UserController userController;
	public Server() {
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		exr = Executors.newFixedThreadPool(4);
	}
	public Server(UserController controller){
		this();
		this.userController = controller;
	}

	public void run() {
		System.out.println("Server Running....");
		while (serverSocket != null) {
			try {
				System.out.println("Client Request Ready");
				Socket socket = serverSocket.accept();
				System.out.println("client accept");
				Runnable task = new ClientHandler(socket, userController);
				exr.submit(task);

			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

	public void send() {

	}




	/*private static void accept() {
		Runnable accept = () -> {
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
			try (serverSocket = new ServerSocket(SERVER_PORT)) {
				Socket socket;
				System.out.println("Server running");
				socket = serverSocket.accept(); // client연결 catch
				System.out.println("before connection");
				ois = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
				oos = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Object data = null;
			while (true) {
				try {
					if ((data = ois.readObject()) == null) break;
					//설계된 프로토콜 메시지를 처리하는 부분
					System.out.println(data);

				} catch (IOException | ClassNotFoundException e) {
					throw new RuntimeException(e);
				}

			}
		};
		exr.submit(accept);
	}*/

}
