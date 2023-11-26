package Container;

import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

public class SerialVersionContainer implements Serializable {
	@Serial
	@Getter
	private static final long serialVersionUID = 362498821L;

}
