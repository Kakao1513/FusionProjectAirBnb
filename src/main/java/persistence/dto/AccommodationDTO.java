package persistence.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
