package network.Client;

import Container.IocContainer;
import Container.ViewContainer;
import network.Client.Handler.ActorHandler;
import network.Client.Handler.AdminHandler;
import network.Client.Handler.GuestHandler;
import network.Client.Handler.HostHandler;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Enums.RoleType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import view.AccommodationView;
import view.UserView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	private final String ip;
	private final int port;
	private final UserView userView;
	private Socket socket;
	private static UserDTO currentUser;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	private final AdminHandler adminHandler;

	private final GuestHandler guestHandler;

	private final HostHandler hostHandler;

	public Client(String ip, int port, ViewContainer viewContainer) {
		this.ip = ip;
		this.port = port;
		try {
			socket = new Socket(ip, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		userView = viewContainer.userView();
		adminHandler = new AdminHandler(viewContainer, oos, ois);
		guestHandler = new GuestHandler(viewContainer, oos, ois);
		hostHandler = new HostHandler(viewContainer, oos, ois);
	}

	private boolean login() {
		boolean isSuccess = false;
		UserDTO loginInfo = userView.loginRequestView();
		Request loginRequest = new Request();
		loginRequest.setRoleType(RoleType.COMMON);
		loginRequest.setMethod(Method.POST);
		loginRequest.setPayloadType(PayloadType.USER);
		loginRequest.setPayload(loginInfo);
		Response response;
		try {
			oos.writeObject(loginRequest);
			oos.flush();
			response = (Response) ois.readObject();
			if (response.getIsSuccess()) {
				currentUser = (UserDTO) response.getPayload();
				System.out.println("로그인 성공");
				isSuccess = true;
			} else {
				System.out.println(response.getErrorMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;
	}

	public void run() {
		try {
			while (!login()) ;
			ActorHandler.setCurrentUser(currentUser);
			int select = 1;
			while (select != 0) {
				int jobOption = -1;
				select = userView.viewJobs(currentUser);
				switch (select) {
					case 0 -> {
						System.out.println("로그아웃.");
					}
					case 1 -> {
						guestHandler.run();
					}
					case 2 -> {
						hostHandler.run();
					}
					case 3 -> {
						adminHandler.run();
					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();
		}finally {
			closeResource();
		}
	}

	private void closeResource() {
		try {
			if (ois != null) {
				ois.close();
			}
			if (oos != null) {
				oos.close();
			}
			if (!socket.isClosed()) {
				socket.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
