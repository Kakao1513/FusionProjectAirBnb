package network.Client;

import network.Protocol.Enums.JobType;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;
import network.Protocol.Request;
import network.Protocol.Response;
import persistence.dto.UserDTO;
import view.UserView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
	private final String ip;
	private final int port;
	UserView userView;
	private Socket socket;

	private UserDTO currentUser;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;

	//contorller

	public Client(String ip, int port, UserView view) {
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
		userView = view;
	}

	private boolean login() {
		boolean isSuccess = false;
		UserDTO loginInfo = userView.loginRequestView();
		Request loginRequest = new Request();
		loginRequest.setJobType(JobType.COMMON);
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
				closeResource();
				socket = new Socket(ip, port);
				oos = new ObjectOutputStream(socket.getOutputStream());
				ois = new ObjectInputStream(socket.getInputStream());
				//해당 방식에 문제가 없는가?
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		return isSuccess;

	}

	public void run() {
		try {
			while (!login()) ;
			userView.viewJobs(currentUser);
		} finally {
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
