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
    int AccomId;
    int userID;
    String accomName;
    String address;
    String type;
    int capacity;
    String comment;
    String status;
}
