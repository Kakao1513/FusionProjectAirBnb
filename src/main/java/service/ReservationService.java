package service;

import Container.IocContainer;
import persistence.dao.ReservationDAO;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationService {
    private ReservationDAO reservationDAO;

    public ReservationService(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
    }

    public ReservationService(IocContainer iocContainer){
        this.reservationDAO = iocContainer.reservationDAO();
    }

    public List<ReservationDTO> getReservationList(AccommodationDTO accomDTO, LocalDate date){
        Map<String, Object> filters = new HashMap<>();
        filters.put("accomID", accomDTO.getAccomId());
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));
        return reservationDAO.getReservations(filters);
    }

    public List<ReservationDTO> checkReservationStatus(int id, int month)
    {
        Map<String, Object> filters = new HashMap<>();
        LocalDate date = LocalDate.now().withMonth(month);

        filters.put("accomID", id);
        filters.put("checkIn", date);
        filters.put("checkOut", date.plusMonths(1));

        return reservationDAO.getReservations(filters);
    }

    public int updateReservation(ReservationDTO rDTO){
        return reservationDAO.updateReservation(rDTO);
    }


}
