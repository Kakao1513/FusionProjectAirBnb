package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.HostDto;
import persistence.dto.UserDTO;
import persistence.mapper.UserMapper;

import java.util.List;

public class UserDAO extends DAO<UserDTO> {


	public UserDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	public void insertHost(UserDTO host) {

	}

	public List<HostDto> selectHost() {
		List<HostDto> DTOs = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			DTOs = mapper.getHost();
		}
		return DTOs;
	}

	public List<UserDTO> selectAll() {
		List<UserDTO> DTOs = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			DTOs = mapper.getAll();
		}
		return DTOs;
	}

	public String getPassword(String id) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.selectPassword(id);
		}
	}

	public UserDTO getUser(String id){
		try (SqlSession session = sqlSessionFactory.openSession()){
			UserMapper mapper = session.getMapper(UserMapper.class);
			return mapper.getUser(id);
		}
	}
}
