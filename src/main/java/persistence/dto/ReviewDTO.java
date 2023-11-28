package persistence.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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