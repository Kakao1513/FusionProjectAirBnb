package network.Protocol.Packet;

import Container.SerialVersionContainer;
import lombok.*;
import persistence.dto.AccommodationDTO;

import java.io.Serial;
import java.io.Serializable;
import java.time.YearMonth;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccommodationByYearMonth implements Serializable {
	@Serial
	private static final long serialVersionUID = SerialVersionContainer.getSerialVersionUID();
	private AccommodationDTO accommodationDTO;
	private YearMonth yearMonth;
}
