package network.Client;

import network.Protocol.LoginRequest;
import network.Protocol.LoginResponse;
import network.Protocol.Protocol;
import network.Protocol.UserLoginInfo;
import persistence.dto.UserDTO;
import view.UserView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Optional;

public class Client {
	private final String ip;
	private final int port;
	private Socket socket;

	private ObjectOutputStream oos;
	private ObjectInputStream ois;


	//contorller

	public Client(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			socket = new Socket(ip, port);
			System.out.println("Client socket open : " + socket.getInetAddress().getCanonicalHostName());
			oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("OutputStream Is Open");
			ois = new ObjectInputStream(socket.getInputStream());
			System.out.println("inputStream Is Open");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		try {
			UserView userView = new UserView();
			//로그인 로직으로 따로 분리시켜야 됨
			UserLoginInfo loginInfo = userView.loginRequestView();
			LoginRequest loginRequest = new LoginRequest();
			loginRequest.setLoginInfo(loginInfo);
			Protocol packet = new Protocol();
			packet.setPacket(Protocol.LOGIN_REQUEST, loginRequest);
			oos.writeObject(packet);
			oos.flush();
			System.out.println("loginRequest successfully write");

			Protocol response = (Protocol) ois.readObject();
			Object responsePacket = response.getPacket();
			LoginResponse loginResponse;
			if (responsePacket != null) {
				loginResponse = (LoginResponse) responsePacket;
				if (loginResponse.isSuccess()) {
					UserDTO loginData = loginResponse.getUserInfo();
					userView.viewMyPage(loginData);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
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
}
