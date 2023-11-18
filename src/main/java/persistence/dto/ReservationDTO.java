package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
public class ReservationDTO extends DTO {
	private int reservationID;
	private int userID;
	private int accommodationID;
	private int roomId;
	private LocalDateTime reserveDate;
	private LocalDateTime checkIn;
	private LocalDateTime checkOut;
	private int charge;
	private String reservationInfo;
}
