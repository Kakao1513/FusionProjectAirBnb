package network;

import Controller.MainController;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private final static int SERVER_PORT = 4000;
	private ExecutorService exr;
	private ServerSocket serverSocket;
	private MainController mainController;

	public Server() throws IOException {
		serverSocket = new ServerSocket(SERVER_PORT);
		exr = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
	}

	public void run() {
		System.out.println("Server Running....");
		while (serverSocket != null) {
			try {
				System.out.println("Client Request Ready");
				Socket socket = serverSocket.accept();
				Runnable task = new ClientHandler(socket);
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

	static class ClientHandler implements Runnable {
		private Socket client;
		private ObjectOutputStream os;
		private ObjectInputStream is;
		private MainController mc;


		ClientHandler(Socket soc) throws IOException {
			client = soc;
			os = new ObjectOutputStream(new BufferedOutputStream(client.getOutputStream()));
			is = new ObjectInputStream(new ObjectInputStream(client.getInputStream()));
		}

		@Override
		public void run() {
			System.out.println("Client Connection.");
			try {
				Optional<Object> o = Optional.of(is.readObject());
				if(o.isPresent()){
					Message message = (Message) o.get();
					Class cl = message.getClass();
					cl.getTypeName();
					/*
					 * 이제 여기에 처리해야할 작업들이 들어감.
					 * is에서 가져온 Message를 기반으로 Controller의 작업이 수행됨.
					 * */
				}
			} catch (IOException | ClassNotFoundException e) {
				throw new RuntimeException(e);
			}

			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
				if (client != null) {
					client.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}
	}
}
