package network.Server;

import Container.IocContainer;
import persistence.dao.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public final static int SERVER_PORT = 4000;
	private final ExecutorService exr;
	private final ServerSocket serverSocket;
	private final IocContainer iocContainer;

	/*private final AccommodationDAO accommodationDAO;
	private final UserDAO userDAO;
	private final ReviewDAO reviewDAO;
	private final ReservationDAO reservationDAO;
	private final RatePolicyDAO ratePolicyDAO;
	private final AmenityDAO amenityDAO;

	public Server(AccommodationDAO accommodationDAO, UserDAO userDAO, ReviewDAO reviewDAO, ReservationDAO reservationDAO, RatePolicyDAO ratePolicyDAO, AmenityDAO amenityDAO) {
		try {
			serverSocket = new ServerSocket(SERVER_PORT);

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		exr = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.amenityDAO = amenityDAO;
		this.accommodationDAO = accommodationDAO;
		this.ratePolicyDAO = ratePolicyDAO;
		this.reviewDAO = reviewDAO;
		this.userDAO = userDAO;
		this.reservationDAO = reservationDAO;
	}*/

	public Server(IocContainer iocContainer){
		try {
			serverSocket = new ServerSocket(SERVER_PORT);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		exr = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		this.iocContainer = iocContainer;
	}

	public void run() {
		System.out.println("Server Running....");
		while (serverSocket != null) {
			try {
				System.out.println("Client Request Ready");
				Socket socket = serverSocket.accept();
				System.out.println("client accept");
				Runnable task = new ClientHandler(socket,iocContainer);
				exr.submit(task);
			} catch (IOException e) {
				System.err.println(e);
			}
		}

	}

	public void send() {

	}




}
