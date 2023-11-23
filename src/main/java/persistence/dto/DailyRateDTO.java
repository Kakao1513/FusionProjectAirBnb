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
public class DailyRateDTO extends DTO
{
    private int AccomId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int Charge;
}
