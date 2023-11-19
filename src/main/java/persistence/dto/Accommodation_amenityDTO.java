package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class Accommodation_amenityDTO extends DTO{
    private String Accom_amenityId;
    private int AccomId;
    private int AmenityID;
}
