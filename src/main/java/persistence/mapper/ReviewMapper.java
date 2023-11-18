package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import persistence.dto.ReviewDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewMapper {
    String selectReviews = "SELECT * FROM Review WHERE AccommodationID = #{accomID};";

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
}