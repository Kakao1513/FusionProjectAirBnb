package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ReservationDTO;
import persistence.mapper.ReservationMapper;
import java.util.List;

public class ReservationDAO {
	SqlSessionFactory sqlSessionFactory;

	public ReservationDAO(SqlSessionFactory sessionFactory) {
		this.sqlSessionFactory = sessionFactory;
	}
	public List<ReservationDTO> getReservations(int accomID, String startDate, String endDate) {
		List<ReservationDTO> DTOS = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ReservationMapper reservationMapper = session.getMapper(ReservationMapper.class);
			DTOS = reservationMapper.selectReservations(accomID, startDate, endDate);
		}
		return DTOS;
	}


}
