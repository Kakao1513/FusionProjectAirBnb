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

		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			AccommodationMapper accommodationMapper = sqlSession.getMapper(AccommodationMapper.class);
			DTOs = accommodationMapper.getAll();
		}
		return DTOs;
	}

	public List<AccommodationDTO> selectConfirm() {
		List<AccommodationDTO> DTOs = null;

		try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
			AccommodationMapper accommodationMapper = sqlSession.getMapper(AccommodationMapper.class);
			DTOs = accommodationMapper.getConfirm();
		}
		return DTOs;
	}
}
