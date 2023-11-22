package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.ReservationDTO;

import java.time.LocalDate;
import java.util.List;
public interface ReservationMapper {
    String getOneMonthReservations =
            """
            SELECT *
            FROM reservation
            WHERE accommodationID = #{accomID}
            AND CheckOut >= #{date}
            AND CheckIn <= DATE_ADD(#{date}, INTERVAL 1 MONTH)
            """;
    String reservationCheck =
            """
            SELECT *
            FROM reservation
            WHERE accommodationID = #{accomID}
            AND roomID = #{roomID}
            AND (ReservationInform = '예약중' OR ReservationInform = '승인대기중')
            AND CheckOut >= #{checkIn}
            AND CheckIn <= #{checkOut}
            """;



    @Select(getOneMonthReservations)
    @Results(
            id = "ReservationResultSet",
            value = {
                    @Result(property = "reservationID", column = "reservationID"),
                    @Result(property = "userID", column = "userID"),
                    @Result(property = "accommodationID", column = "accommodationID"),
                    @Result(property = "roomID", column = "roomID"),
                    @Result(property = "reserveDate", column = "reserveDate"),
                    @Result(property = "checkIn", column = "checkIn"),
                    @Result(property = "checkOut", column = "checkOut"),
                    @Result(property = "charge", column = "charge"),
                    @Result(property = "reservationInfo", column = "reservationInfo"),
            }
    )
    List<ReservationDTO> selectReservations(@Param("accomID") int accomID,
                                            @Param("date") LocalDate date);

    @Select(reservationCheck)
    @ResultMap("ReservationResultSet")
    List<ReservationDTO> reservationCheck(@Param("accomID") int accomID, @Param("roomID") int roomID, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Insert("INSERT INTO reservation (UserID, AccommodationID, RoomID, ReserveDate, CheckIn, CheckOut, Charge, ReservationInfo) VALUES (#{userId})") //TODO:추가작성
    void insertReservation(ReservationDTO rDTO);

}
