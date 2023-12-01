package network.Protocol.Packet;

import Container.SerialVersionContainer;
import lombok.*;
import persistence.dto.AccommodationDTO;

import java.io.Serial;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AccommodationCharge implements Serializable {
	@Serial
	private static final long serialVersionUID = SerialVersionContainer.getSerialVersionUID();
	private AccommodationDTO accom;
	private int charge;
}
