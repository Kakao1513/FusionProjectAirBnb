package persistence.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyRateDTO extends DTO {
	private int AccomId;
	private LocalDate startDate;
	private LocalDate endDate;
	private int Charge;

}
