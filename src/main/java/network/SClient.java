package network;

import java.io.*;
import java.net.Socket;

public class SClient {
	private final String ip;
	private final int port;

	private Socket socket;

	private ObjectOutputStream objectOutputStream;
	private ObjectInputStream objectInputStream;

	private BufferedReader bufferedReader;

	//contorller

	public SClient(String ip, int port){
		this.ip = ip;
		this.port = port;
		try {
			socket = new Socket(ip,port);
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	public void run(){

	}
}
