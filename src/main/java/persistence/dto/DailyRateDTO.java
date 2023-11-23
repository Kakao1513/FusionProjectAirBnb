package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class DailyRateDTO extends DTO
{
    private int AccomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private int Charge;
}
