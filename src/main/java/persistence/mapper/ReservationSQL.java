package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.ReservationDTO;

import java.time.LocalDate;

public class ReservationSQL {
    public static String selectReservations(
            @Param("userID") Integer userID,
            @Param("accomID") Integer accomID,
            @Param("roomID") Integer roomID,
            @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut,
            @Param("status") String status,
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
        if (roomID != null){
            sql.WHERE("roomID = #{roomID}");
            sql.WHERE("(reservationInfo = '예약중' OR reservationInfo = '승인대기중')");
        }
        if (checkIn != null){
            sql.WHERE("checkOut >= #{checkIn}");
        }
        if (now != null){
            // 숙박이 완료된 예약에 대해서만 조회
            sql.WHERE("checkOut <= #{now}");
        }
        if (checkOut != null){
            sql.WHERE("checkIn <= #{checkOut}");
        }
        if (status != null){
            sql.WHERE("reservationInfo = #{status}");
        }

        return sql.toString();
    }

    public static String insertReservation(ReservationDTO reservation){
        SQL sql = new SQL()
                .INSERT_INTO("Reservation")
                .INTO_COLUMNS("UserID, AccommodationID, RoomID, ReserveDate, CheckIn, CheckOut, Charge, ReservationInfo")
                .INTO_VALUES("#{userID}, #{accommodationID} #{accommodationID}, #{roomID}, #{reserveDate}, #{checkIn}, #{checkOut}, #{charge}, #{reservationInfo}");

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
    }

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
    }
}
