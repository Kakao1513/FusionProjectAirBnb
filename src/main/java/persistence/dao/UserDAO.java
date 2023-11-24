package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.UserDTO;
import persistence.mapper.UserMapper;

import java.util.List;

public class UserDAO extends DAO<UserDTO> {


	public UserDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	public void insertHost(UserDTO host) {

	}

	public List<UserDTO> selectAll() {
		List<UserDTO> DTOs = null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			DTOs = mapper.getAll();
		}catch (Exception e){
			e.printStackTrace();
		}
		return DTOs;
	}

	public String selectPwById(String id) {
		String password =null;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			UserMapper mapper = session.getMapper(UserMapper.class);
			password = mapper.selectPassword(id);
		}catch (Exception e){
			e.printStackTrace();
		}
		return password;
	}

	public UserDTO selectById(String id){
		UserDTO userDTO = null;
		try (SqlSession session = sqlSessionFactory.openSession()){
			UserMapper mapper = session.getMapper(UserMapper.class);
			userDTO = mapper.selectById(id);
		}catch (Exception e){
			e.printStackTrace();
		}
		return userDTO;
	}

	public void updateUser(UserDTO dto){
		try (SqlSession session = sqlSessionFactory.openSession()){
			UserMapper mapper = session.getMapper(UserMapper.class);
			mapper.updateUser(dto);
			session.commit();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
