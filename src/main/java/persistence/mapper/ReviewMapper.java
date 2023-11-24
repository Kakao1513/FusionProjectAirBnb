package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.ReviewDTO;

import java.util.List;

public interface ReviewMapper {
    @SelectProvider(type = ReviewSQL.class, method = "selectReviews")
    @Results(
            id = "ReviewResultSet",
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

    @InsertProvider(type = ReviewSQL.class, method = "insertReview")
    int insertReview(ReviewDTO review);

}