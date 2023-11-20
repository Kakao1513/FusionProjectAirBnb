package network.Protocol;

import lombok.Getter;
import lombok.Setter;
import network.Protocol.Enums.JobType;
import network.Protocol.Enums.Method;
import network.Protocol.Enums.PayloadType;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Request implements Serializable {
	@Serial
	private static final long serialVersionUID = 362498821L;

	private Method method;  //header
	private PayloadType payloadType;  //header
	private JobType jobType; //header

	private Object payload;
}
