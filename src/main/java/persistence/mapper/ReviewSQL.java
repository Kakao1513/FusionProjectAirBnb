package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.ReviewDTO;
public class ReviewSQL {

    public static String selectReviews(@Param("accomID") int accomID){
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("Review")
                .WHERE("AccommodationID = #{accomID}");

        return sql.toString();
    }
    public static String insertReview(ReviewDTO reservation){
        SQL sql = new SQL()
                .INSERT_INTO("Review")
                .INTO_COLUMNS("accommodationID, userID, roomID, text, dateCreated, rate")
                .INTO_VALUES("#{review.accommodationID}, #{review.userID}, #{review.roomID}")
                .INTO_VALUES("#{review.text}, #{review.dateCreated}, #{review.rate}");
        if (reservation.getParentID() != null){
            sql.INTO_COLUMNS("parentCommentID").INTO_VALUES("#{parentCommentID}");
        }
        if (reservation.getModifiedDate() != null){
            sql.INTO_COLUMNS("modificationTime").INTO_VALUES("#{modificationTime}");
        }

        return  sql.toString();
    }

}
