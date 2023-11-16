package persistence.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@Builder
public class ReservationDTO extends DTO {
	private int reservationId;
	private int userId;
	private int accommodationId;
	private int roomId;
	private Date reserveDate;
	private Date checkIn;
	private Date checkOut;
	private int charge;
	private String reservationInfo;
}
