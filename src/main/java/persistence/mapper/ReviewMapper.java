package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.ReviewDTO;

import java.util.List;

public interface ReviewMapper {
    String selectReviews = "SELECT * FROM Review WHERE AccommodationID = #{accomID};";
    String insertReview = """
            INSERT INTO `reservation`
            (`accommodationID`, `userID`, `roomID`, 
            `parentCommentID`, `text`, `dateCreated`, 
            `modificationTime`, `rate`)
            VALUES
            (#{review.accommodationID}, #{review.userID}, #{review.roomID},
             #{review.parentCommentID}, #{review.text}, #{review.dateCreated},
             #{review.modificationTime}, #{review.rate});
            """;

    @Select(selectReviews)
    @Results(
            id = "AmenityResultSet",
            value = {
                    @Result(property = "commentID", column = "commentID"),
                    @Result(property = "accomID", column = "accommodationID"),
                    @Result(property = "userID", column = "userID"),
                    @Result(property = "roomID", column = "roomID"),
                    @Result(property = "parentID", column = "parentCommentID"),
                    @Result(property = "text", column = "text"),
                    @Result(property = "createdDate", column = "dateCreated"),
                    @Result(property = "modifiedDate", column = "modificationTime"),
                    @Result(property = "rate", column = "rate"),
            }
    )
    List<ReviewDTO> selectReviews(@Param("accomID") int accomID);

    @Select(insertReview)
    int insertAccom(@Param("review") ReviewDTO review);


}