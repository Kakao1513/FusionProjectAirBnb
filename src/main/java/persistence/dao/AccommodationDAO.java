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

	public List<AccommodationDTO> selectAll() {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.getAll();
		}
		return DTOs;
	}

	public List<AccommodationDTO> selectByStatus(String status) {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.selectByStatus(status);
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

	public List<AccommodationDTO> selectByDate(String startDate, String endDate) {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.selectByDate(startDate, endDate);
		}
		return DTOs;
	}

}
