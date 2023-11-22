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
import java.sql.Date;
import java.util.Scanner;

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
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
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
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSuccess;

	}

	public void run() {
		try {
			while (!login()) ;
			int select = 1;
			while (select != 0) {
				select = userView.viewJobs(currentUser);
				switch (select) {
					case 0 -> {
						System.out.println("로그아웃.");
					}
					case 1 -> {
						int jobOption = userView.selectGuestJob();
						selectGuestJob(jobOption);
					}
					case 2 -> {

					}
					case 3 -> {

					}
					default -> {

					}
				}
			}
		} finally {
			closeResource();
		}
	}

	public void selectGuestJob(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로.");
				return;
			}
			case 1 -> {
				int jopOption = userView.selectMyPageJobs(currentUser);
				myPageJobHandle(jopOption);
			}
			case 2 -> {

			}
			case 3 -> {

			}
			case 4 -> {

			}
		}
	}

	public void myPageJobHandle(int select) {
		switch (select) {
			case 0 -> {
				System.out.println("이전 페이지로 돌아갑니다.");
			}
			case 1 -> {

			}
			case 2 -> {

			}
			case 3 -> {

			}
			case 4 -> {
				changePrivacy();
			}
		}
	}

	public void changePrivacy() {
		Scanner sc = new Scanner(System.in);
		String newName, newPhone;
		Date newBirth;
		System.out.println("이름 입력:");
		newName = sc.nextLine();
		System.out.println("생일 입력:");
		newBirth = Date.valueOf(sc.nextLine());
		System.out.println("전화번호 입력:");
		newPhone = sc.nextLine();

		Request req = new Request();
		req.setMethod(Method.PUT);
		req.setJobType(JobType.GUEST);
		req.setPayloadType(PayloadType.USER);
		UserDTO userDTO = new UserDTO(currentUser);
		Object[] body = {userDTO, newName, newBirth, newPhone};
		req.setPayload(body);
		Response response;
		try {
			oos.writeObject(req);
			oos.flush();

			response = (Response) ois.readObject();
			if (response.getIsSuccess()) {
				System.out.println("개인정보 수정 성공.");
				currentUser = (UserDTO) response.getPayload();
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
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
