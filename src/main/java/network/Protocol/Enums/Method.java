package network.Protocol.Enums;

import java.io.Serial;
import java.io.Serializable;

public enum Method implements Serializable {
	GET, POST, PUT, DELETE;

	@Serial
	private static final long serialVersionUID = 362498821L;
}
