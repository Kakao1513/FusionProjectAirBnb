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
    int userID;
    String houseName;
    String address;
    String accommodationType;
    int capacity;
    String accommodationComment;
    String status;
}
