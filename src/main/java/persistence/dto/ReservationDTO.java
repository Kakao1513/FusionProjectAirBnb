package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
public class ReservationDTO extends DTO {
	private int reservationID;
	private int userID;
	private int accommodationID;
	private int roomID;
	private LocalDateTime reserveDate;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int charge;
	private String reservationInfo;
}
