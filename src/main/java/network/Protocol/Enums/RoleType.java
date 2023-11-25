package network.Protocol.Enums;

import java.io.Serial;
import java.io.Serializable;

public enum RoleType implements Serializable {
	ADMIN, HOST, GUEST, COMMON; //평가 기준 기능별 분류

	@Serial
	private static final long serialVersionUID = 362498821L;
}
