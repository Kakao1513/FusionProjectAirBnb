package network.Protocol;

import lombok.*;
import network.Protocol.Enums.RoleType;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response implements Serializable {

	@Serial
	private static final long serialVersionUID = 362498821L;

	private Method method;  //header
	private PayloadType payloadType;  //header
	private RoleType roleType; //header

	private Boolean isSuccess;
	private String message;

	private Object payload;

}
