package network.Protocol.Packet;

import Container.SerialVersionContainer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import persistence.dto.AccommodationDTO;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRegister implements Serializable {

	@Serial
	private static final long serialVersionUID = SerialVersionContainer.getSerialVersionUID();
	List<Boolean[]> amenityList;
	AccommodationDTO accommodationDTO;
}
