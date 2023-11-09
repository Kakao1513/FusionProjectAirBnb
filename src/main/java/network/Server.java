package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Server {
	private final static int SERVER_PORT = 4000;

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		ServerSocket serverSocket;
		Socket socket = null;

		ObjectInputStream objectInputStream;
		ObjectOutputStream objectOutputStream;
		PrintWriter printWriter = null;
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("Server Running");
			System.out.println("before socket connect");
			socket = serverSocket.accept();
			System.out.println("after socket connect");
			objectInputStream = new ObjectInputStream(socket.getInputStream());
			objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		Optional<Object> o = Optional.empty();
		while (!o.isEmpty()){

		}

	}
}
