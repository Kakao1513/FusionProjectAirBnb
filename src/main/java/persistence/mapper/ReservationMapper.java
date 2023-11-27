package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.ReservationDTO;
import persistence.dto.RoomDTO;

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
                    @Result(property = "headcount", column = "headCount"),
                    @Result(property = "reserveDate", column = "reserveDate"),
                    @Result(property = "checkIn", column = "checkIn"),
                    @Result(property = "checkOut", column = "checkOut"),
                    @Result(property = "reservationInfo", column = "ReservationInfo"),
            }
    )
    List<ReservationDTO> selectReservations(Map<String, Object> filters);

    @InsertProvider(type = ReservationSQL.class, method = "insertReservation")
    void insertReservation(ReservationDTO rDTO);

    @UpdateProvider(type = ReservationSQL.class, method = "updateReservation")
    int updateReservation(ReservationDTO rDTO);

    @UpdateProvider(type = ReservationSQL.class, method = "updateGuestReservation")
    int updateGuestReservation(ReservationDTO reservDTO);
/*
    @SelectProvider(type = ReservationSQL.class, method = "getAvailableRoomList")
    @Results(
            id = "RoomResultSet",
            value = {
                    @Result(property = "roomID", column = "roomID"),
                    @Result(property = "accomID", column = "accommodationID"),
            }
    )
    List<RoomDTO> getAvailableRoomList(ReservationDTO reservationDTO);*/

}
