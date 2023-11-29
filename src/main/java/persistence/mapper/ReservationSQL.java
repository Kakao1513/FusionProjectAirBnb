package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.ReservationDTO;

import java.time.LocalDate;

public class ReservationSQL {
    public static String selectReservations(
            @Param("userID") Integer userID,
            @Param("accomID") Integer accomID,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("reservationInfo") String reservationInfo,
            @Param("notCancel") Boolean notCancel,
            @Param("now") LocalDate now
    ) {
        SQL sql = new SQL()
                .SELECT("*")
                .FROM("Reservation");
        if (userID != null){
            sql.WHERE("userID = #{userID}");
        }
        if (accomID != null){
            sql.WHERE("accommodationID = #{accomID}");
        }
        if (checkIn != null){
            sql.WHERE("checkOut >= #{checkIn}");
        }
        if (checkOut != null){
            sql.WHERE("checkIn <= #{checkOut}");
        }
        if (now != null){
            // 숙박이 완료된 예약에 대해서만 조회
            sql.WHERE("checkOut <= #{now}");
        }
        if (reservationInfo != null){
            sql.WHERE("reservationInfo = #{reservationInfo}");
        }
        if (notCancel){
            sql.WHERE("reservationInfo != '취소됨'");
        }

        return sql.toString();
    }

    public static String insertReservation(ReservationDTO reservation){
        SQL sql = new SQL()
                .INSERT_INTO("Reservation")
                .INTO_COLUMNS("UserID, AccommodationID, Headcount, ReserveDate, CheckIn, CheckOut, ReservationInfo")
                .INTO_VALUES("#{userID}, #{accommodationID}, #{headcount}, #{reserveDate}, #{checkIn}, #{checkOut}, #{reservationInfo}");

        return  sql.toString();
    }

    public static String updateReservation(ReservationDTO reservation){
        SQL sql = new SQL()
                .UPDATE("Reservation")
                .SET("reservationInfo = #{reservationInfo}")
                .WHERE("reservationID = #{reservationID}");
        return sql.toString();
    }

    public static String updateGuestReservation(ReservationDTO reservation) {
        SQL sql = new SQL()
                .UPDATE("Reservation")
                .SET("reservationInfo = #{reservationinfo}")
                .WHERE("AccommodationID IN(SELECT AccommodationID FROM airbnb.accommodation")
                .AND()
                .WHERE("AccommodationID = #{accommodationID}");
        return sql.toString();
    }/*

    public static String getAvailableRoomList(ReservationDTO reservationDTO){
        SQL subQuery = new SQL()
                .SELECT("r.RoomID, r.AccommodationID")
                .FROM("reservation r")
                .WHERE("CheckOut >= #{checkIn}")
                .WHERE("CheckIn <= #{checkOut}");
        SQL query = new SQL()
                .SELECT("*")
                .FROM("Room")
                .WHERE("accommodationID = #{accommodationID}")
                .WHERE("(RoomID, AccommodationID) NOT IN(" + subQuery.toString() + ")");

        return query.toString();
    }*/
}
