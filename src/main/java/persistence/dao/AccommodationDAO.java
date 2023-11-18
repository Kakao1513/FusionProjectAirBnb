package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;
import persistence.mapper.AccommodationMapper;

import java.util.List;

public class AccommodationDAO extends DAO<AccommodationDTO> {
	public AccommodationDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}
	public List<AccommodationDTO> selectAccom(
			String status,
		  	String accomName,
		  	String startDate, String endDate,
		  	String capacity,
		  	String accomType
	) {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.selectAccom(
					status,
					accomName,
					startDate, endDate,
					capacity,
					accomType
			);
		}
		return DTOs;
	}

	public void insertAccom(AccommodationDTO dto){
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			accomMapper.insertAccom(dto);
			session.commit();
		}
	}

	public AccommodationDTO getAccom(int accomID){
		AccommodationDTO DTO = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTO = accomMapper.getAccom(accomID);
		}
		return DTO;
	}

}
