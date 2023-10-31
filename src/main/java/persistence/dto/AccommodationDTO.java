package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class AccommodationDTO {
    int UserID;
    String HouseName;
    String Address;
    int Capacity;
    String AccommodationComment;
    boolean AccommodationStatus;
}
