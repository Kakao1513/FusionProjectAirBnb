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
public class DiscountPolicyDTO extends DTO {
	private int accomID;
	private String discountType;
	private int value;
	private LocalDate dateStart;
	private LocalDate dateEnd;
}