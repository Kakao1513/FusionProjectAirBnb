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
    private int accomID;
    private int userID;
    private String accomName;
    private String address;
    private String type;
    private int capacity;
    private String comment;
    private String status;
}
