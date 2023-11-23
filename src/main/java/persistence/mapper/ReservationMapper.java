package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.ReservationDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface ReservationMapper {
    @SelectProvider(type = ReservationSQL.class, method = "selectReservations")
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
                    @Result(property = "reservationInfo", column = "ReservationInform"),
            }
    )
    List<ReservationDTO> selectReservations(Map<String, Object> filters);

    @Insert("INSERT INTO reservation (UserID, AccommodationID, RoomID, ReserveDate, CheckIn, CheckOut, Charge, ReservationInform) " +
            "VALUES (#{userID}, #{accommodationID} #{accommodationID}, #{roomID}, #{reserveDate}, #{checkIn}, #{checkOut}, #{charge}, #{reservationInfo} )") //TODO:추가작성
    void insertReservation(ReservationDTO rDTO);

}
