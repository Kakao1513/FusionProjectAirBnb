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
                .INTO_COLUMNS("accommodationID, userID, text, dateCreated")
                .INTO_VALUES("#{review.accommodationID}, #{review.userID}")
                .INTO_VALUES("#{review.text}, #{review.dateCreated}");
        if (reservation.getParentID() != null){
            sql.INTO_COLUMNS("parentCommentID").INTO_VALUES("#{parentCommentID}");
        }
        if (reservation.getRate() != null){
            sql.INTO_COLUMNS("rate").INTO_VALUES("#{review.rate}");
        }

        return  sql.toString();
    }

}
