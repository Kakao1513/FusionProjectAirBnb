package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class AmenityDTO extends DTO {
    int amenityID;
    String name;
    String category;
}
