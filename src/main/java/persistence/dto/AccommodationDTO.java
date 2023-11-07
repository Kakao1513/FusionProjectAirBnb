package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AccommodationDTO extends DTO{
    int UserID;
    String HouseName;
    String Address;
    int Capacity;
    String AccommodationComment;
    boolean AccommodationStatus;
}
