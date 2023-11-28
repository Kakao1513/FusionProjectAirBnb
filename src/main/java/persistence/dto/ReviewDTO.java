package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ReviewDTO extends DTO {
    private int commentID;
    private int reservationID;
    private int accomID;
    private int userID;
    private Integer parentID;
    private String text;
    private LocalDateTime createdDate;
    private Integer rate;
}