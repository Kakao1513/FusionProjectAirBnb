package network.Protocol;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Setter
public class Protocol implements Serializable {

	@Serial
	private static final long serialVersionUID = 362498820L;

	public static final int UNDEFINED = -1;
	public static final int LOGIN_REQUEST = 1;
	public static final int LOGIN_RESPONSE = 2;
	protected int protocolType;
	protected Object packet;

	public Protocol() {
		this(UNDEFINED);
	}

	public Protocol(int type) {
		this.protocolType = type;
	}
	public void setPacket(int type, Object bodyPacket){
		protocolType = type;
		packet = bodyPacket;
	}
}
