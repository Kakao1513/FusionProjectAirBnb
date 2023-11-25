package network.Protocol.Enums;

import java.io.Serial;
import java.io.Serializable;

public enum PayloadType implements Serializable {
	USER, ACCOMMODATION, REVIEW, RESERVATION;

	@Serial
	private static final long serialVersionUID = 362498821L;
}
