package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.mapper.Mapper;
import persistence.mapper.UserMapper;

import java.util.List;

public abstract class DAO<E> {
	protected SqlSessionFactory sqlSessionFactory;

	public DAO(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	abstract public List<E> selectAll();
}
