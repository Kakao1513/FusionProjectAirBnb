package persistence.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO extends DTO {
	private int reservationID;
	private int userID;
	private int accommodationID;
	private int headcount;
	private LocalDateTime reserveDate;
	private LocalDate checkIn;
	private LocalDate checkOut;
	private int charge;
	private String reservationInfo;
}
