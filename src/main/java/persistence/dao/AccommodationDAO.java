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

	public List<AccommodationDTO> selectConfirm() {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AccommodationMapper accomMapper = session.getMapper(AccommodationMapper.class);
			DTOs = accomMapper.getConfirm();
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

}
