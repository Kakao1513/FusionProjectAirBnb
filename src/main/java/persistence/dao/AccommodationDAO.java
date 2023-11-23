package persistence.dao;

import Enums.AccommodationStatus;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;
import persistence.mapper.AccommodationMapper;

import java.util.List;
import java.util.Map;

public class AccommodationDAO extends DAO<AccommodationDTO> {
	public AccommodationDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}
	public List<AccommodationDTO> selectAccom(Map<String, Object> filters) {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.selectAccom(filters);
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
	
	public void updateAccomStatus(int id, AccommodationStatus status)
	{
		try (SqlSession session = sqlSessionFactory.openSession())
		{
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			accomMapper.updateAccomStatus(id, status);
			session.commit();
		}
	}
}
