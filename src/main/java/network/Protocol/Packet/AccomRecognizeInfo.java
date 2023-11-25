package network.Protocol.Packet;

import Enums.AccommodationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccomRecognizeInfo implements Serializable {

	@Serial
	private static final long serialVersionUID = 362498821L;
	private Integer accomID;
	private AccommodationStatus status;
}
