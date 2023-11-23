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
public class DiscountPolicyDTO extends DTO
{
    private int AccomId;
    private String DiscountType;
    private int Value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}