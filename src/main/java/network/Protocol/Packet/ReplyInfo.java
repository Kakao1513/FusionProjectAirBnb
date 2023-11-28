package network.Protocol.Packet;

import Container.SerialVersionContainer;
import lombok.*;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReviewDTO;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyInfo implements Serializable {
	@Serial
	private static final long serialVersionUID = SerialVersionContainer.getSerialVersionUID();

	private ReviewDTO reviewDTO;
	private AccommodationDTO accommodationDTO;
}
