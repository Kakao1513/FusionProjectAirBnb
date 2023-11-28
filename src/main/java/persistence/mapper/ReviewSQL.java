package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.ReviewDTO;

import java.time.LocalDateTime;

public class ReviewSQL {

    public static String selectReviews(@Param("accomID") int accomID){
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("Review")
                .WHERE("AccommodationID = #{accomID}");

        return sql.toString();
    }

    public static String insertReview(ReviewDTO review){
        SQL sql = new SQL()
                .INSERT_INTO("Review")
                .INTO_COLUMNS("reservationID, accommodationID, userID, text, dateCreated, parentCommentID, rate")
                .INTO_VALUES("#{reservationID}, #{accomID}, #{userID}, #{text}")
                .INTO_VALUES("#{createdDate}, #{parentID}, #{rate}");

        return  sql.toString();
    }

}
