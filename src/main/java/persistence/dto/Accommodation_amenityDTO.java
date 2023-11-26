package persistence.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Accommodation_amenityDTO extends DTO {
    private String accommodationAmenityID;
    private int accommodationID;
    private int amenityID;
}
