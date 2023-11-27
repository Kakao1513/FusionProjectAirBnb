package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.dto.RoomDTO;
import persistence.mapper.AccommodationMapper;
import persistence.mapper.ReservationMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReservationDAO {
	SqlSessionFactory sqlSessionFactory;

	public ReservationDAO(SqlSessionFactory sessionFactory) {
		this.sqlSessionFactory = sessionFactory;
	}

	public List<ReservationDTO> getReservations(Map<String, Object> filters) {
		List<ReservationDTO> DTOS = new ArrayList<>();

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			DTOS = reservationMapper.selectReservations(filters);
		}catch (Exception e){
			e.printStackTrace();
		}
		return DTOS;
	}

	public int  insertReservation(ReservationDTO rDTO) {
		int num = 0;
		try (SqlSession session = sqlSessionFactory.openSession()){
            ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			num = reservationMapper.insertReservation(rDTO);
			session.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
		return num;
	}

	public int updateReservation(ReservationDTO rDTO) {
		int num = 0;
		try (SqlSession session = sqlSessionFactory.openSession()){
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			num = reservationMapper.updateReservation(rDTO);
			session.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
		return num;
	}

	public int updateGuestReservation(ReservationDTO reservDTO) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			reservationMapper.updateGuestReservation(reservDTO);
		}catch (Exception e){
			e.printStackTrace();
		}
		return 0;
	}
/*
	public List<RoomDTO> getAvailableRoomList(ReservationDTO reservationDTO){
		List<RoomDTO> DTOS = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			DTOS = reservationMapper.getAvailableRoomList(reservationDTO);
		}catch (Exception e){
			e.printStackTrace();
		}
		return DTOS;
	}*/

}
