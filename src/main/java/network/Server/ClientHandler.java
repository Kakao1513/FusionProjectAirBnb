package network.Server;

import Controller.MainController;
import Controller.UserController;
import network.Protocol.LoginRequest;
import network.Protocol.LoginResponse;
import network.Protocol.Protocol;
import network.Protocol.UserLoginInfo;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dao.UserDAO;
import persistence.dto.UserDTO;
import service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.Optional;

class ClientHandler implements Runnable {
	private Socket client;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private UserController userController;


	ClientHandler(Socket soc, UserController userController) throws IOException {
		client = soc;
		oos = new ObjectOutputStream(client.getOutputStream());
		System.out.println("Server OutputStream Is Open");
		ois = new ObjectInputStream(client.getInputStream());
		System.out.println("Server InputStream Is Open");
		this.userController = userController;
	}

	@Override
	public void run() {
		System.out.println("Client Connection.");
		try {
			//컨트롤러 로직으로 분리
			Object o = ois.readObject();
			Protocol message = (Protocol) o;
			int protNum = message.getProtocolType();
			Object packet = message.getPacket();
			UserDTO userDTO;
			Protocol send = new Protocol(Protocol.LOGIN_RESPONSE);
			if(packet!=null) {
				if (protNum == Protocol.LOGIN_REQUEST) {
					LoginRequest req = (LoginRequest) packet;
					UserLoginInfo userInfo = req.getLoginInfo();
					userDTO = userController.login(userInfo);
					LoginResponse sendResponse = new LoginResponse();
					if(userDTO==null){
						sendResponse.setSuccess(false);
					}else{
						sendResponse.setSuccess(true);
						sendResponse.setUserInfo(userDTO);
					}
					send.setPacket(sendResponse);
				}

				oos.writeObject(send);
				oos.flush();

			}
			//Protocol protocol = (Protocol) o.get();

			/*
			 * 이제 여기에 처리해야할 작업들이 들어감.
			 * is에서 가져온 Message를 기반으로 Controller의 작업이 수행됨.
			 * */
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
}