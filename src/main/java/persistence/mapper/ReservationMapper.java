package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.ReservationDTO;

import java.util.List;
public interface ReservationMapper {
    String getReservations =
            """
            SELECT * 
            FROM reservation 
            WHERE accommodationID = #{accomID} 
            AND CheckOut >= date(#{startDate}) 
            AND CheckIn <= date(#{endDate});
            """;
    @Select(getReservations)
    @Results(
            id = "ReservationResultSet",
            value = {
                    @Result(property = "reservationID", column = "reservationID"),
                    @Result(property = "userID", column = "userID"),
                    @Result(property = "accommodationID", column = "accommodationID"),
                    @Result(property = "reserveDate", column = "reserveDate"),
                    @Result(property = "checkIn", column = "checkIn"),
                    @Result(property = "checkOut", column = "checkOut"),
                    @Result(property = "charge", column = "charge"),
                    @Result(property = "reservationInfo", column = "reservationInfo"),
            }
    )
    List<ReservationDTO> selectReservations(@Param("accomID") int accomID,
                                            @Param("startDate") String startDate, @Param("endDate") String endDate);



}
