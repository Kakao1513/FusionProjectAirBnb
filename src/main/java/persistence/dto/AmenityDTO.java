package persistence.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmenityDTO extends DTO {
    int amenityID;
    String name;
    String category;
}
