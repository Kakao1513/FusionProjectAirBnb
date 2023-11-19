package network.Protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class LoginRequest implements Serializable {
	@Serial
	private static final long serialVersionUID = 362498820L;
	UserLoginInfo loginInfo;
}
