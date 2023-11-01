package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.UserDTO;
import persistence.mapper.Mapper;
import persistence.mapper.UserMapper;

import java.util.List;

public class UserDAO extends DAO<UserDTO>{

	public UserDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	@Override
	public List<UserDTO> selectAll() {
		List<UserDTO> DTOs = null;
		SqlSession session = sqlSessionFactory.openSession();
		try {
			UserMapper mapper = session.getMapper(UserMapper.class);
			DTOs = mapper.getAll();
		} finally {
			session.close();
		}
		return DTOs;
	}
}
