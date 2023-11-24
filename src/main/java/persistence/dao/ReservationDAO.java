package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReservationDTO;
import persistence.mapper.AccommodationMapper;
import persistence.mapper.ReservationMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReservationDAO {
	SqlSessionFactory sqlSessionFactory;

	public ReservationDAO(SqlSessionFactory sessionFactory) {
		this.sqlSessionFactory = sessionFactory;
	}

	public List<ReservationDTO> getReservations(Map<String, Object> filters) {
		List<ReservationDTO> DTOS = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			DTOS = reservationMapper.selectReservations(filters);
		}
		return DTOS;
	}

	public void insertReservation(ReservationDTO rDTO) {
		try (SqlSession session = sqlSessionFactory.openSession()){
            ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			reservationMapper.insertReservation(rDTO);
		}
	}

	public int updateReservation(ReservationDTO rDTO) {
		int num = 0;
		try (SqlSession session = sqlSessionFactory.openSession()){
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			num = reservationMapper.updateReservation(rDTO);
			session.commit();
		}
		return num;
	}


}
