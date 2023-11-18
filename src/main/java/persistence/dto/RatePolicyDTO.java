package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@Builder
public class RatePolicyDTO extends DTO {
    private int accomID;
    private int weekday;
    private int weekend;
}