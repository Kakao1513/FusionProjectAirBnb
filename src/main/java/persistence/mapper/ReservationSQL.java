package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.ReservationDTO;

import java.time.LocalDate;

public class ReservationSQL {
    public static String selectReservations(
            @Param("accomID") Integer accomID,
            @Param("roomID") Integer roomID,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut
    ) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("Reservation");
        if (accomID != null){
            sql.WHERE("accommodationID = #{accomID}");
        }
        if (roomID != null){
            sql.WHERE("roomID = #{roomID}");
            sql.WHERE("ReservationInform = '예약중' OR ReservationInform = '승인대기중'");
        }
        if (checkIn != null){
            sql.WHERE("CheckOut >= #{checkIn}");
        }
        if (checkOut != null){
            sql.WHERE("CheckIn <= #{checkOut}");
        }

        return sql.toString();
    }

    public static String insertReservation(ReservationDTO reservation){
        SQL sql = new SQL()
                .INSERT_INTO("Reservation")
                .INTO_COLUMNS("UserID, AccommodationID, RoomID, ReserveDate, CheckIn, CheckOut, Charge, ReservationInform")
                .INTO_VALUES("#{userID}, #{accommodationID} #{accommodationID}, #{roomID}, #{reserveDate}, #{checkIn}, #{checkOut}, #{charge}, #{reservationInfo}");

        return  sql.toString();
    }

    public static String updateReservation(ReservationDTO reservation){
        SQL sql = new SQL()
                .UPDATE("Reservation")
                .SET("reservationInform = #{reservationInfo}")
                .WHERE("reservationID = #{reservationID}");
        return sql.toString();
    }

}
