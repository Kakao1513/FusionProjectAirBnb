package network.Protocol;

import lombok.Getter;
import lombok.Setter;
import persistence.dto.UserDTO;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class LoginResponse implements Serializable {

	@Serial
	private static final long serialVersionUID = 362498820L;
	private boolean isSuccess;
	private String errorMessage;

	private UserDTO userInfo;

}
