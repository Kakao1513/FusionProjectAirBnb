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
            @Param("status") String status
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
            sql.WHERE("Reservationinfo = '예약중' OR Reservationinfo = '승인대기중'");
        }
        if (checkIn != null){
            sql.WHERE("CheckOut >= #{checkIn}");
        }
        if (checkOut != null){
            sql.WHERE("CheckIn <= #{checkOut}");
        }
        if (status != null){
            sql.WHERE("Reservationinfo = #{status}");
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
                .SET("reservationinfo = #{reservationInfo}")
                .WHERE("reservationID = #{reservationID}");
        return sql.toString();
    }

    public static String updateGuestReservation(ReservationDTO reservation) {
        SQL sql = new SQL()
                .UPDATE("Reservation")
                .SET("Reservationinfo = #{reservationinfo}")
                .WHERE("AccommodationID IN(SELSCT AccommodationID FROM airbnb.accommodation")
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
