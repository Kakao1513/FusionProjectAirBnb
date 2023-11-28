package network.Protocol.Packet;

import Container.SerialVersionContainer;
import lombok.*;
import persistence.dto.ReservationDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationInfo implements Serializable {
	@Serial
	private static final long serialVersionUID = SerialVersionContainer.getSerialVersionUID();
	private List<ReservationDTO> reservationDTOS;
	private List<String> AccommodationName;
	private List<String> UserName;
}
