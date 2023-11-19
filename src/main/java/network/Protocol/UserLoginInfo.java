package network.Protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserLoginInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 362498820L;

	private String id;
	private String pw;

	public UserLoginInfo(){
		id = "";
		pw = "";
	}
	public UserLoginInfo(String id, String pw){
		this.id = id;
		this.pw = pw;
	}

}
