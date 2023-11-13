package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.lang.reflect.Executable;

public abstract class DAO<E> {
	protected final SqlSessionFactory sqlSessionFactory;

	public DAO(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}
	protected int insert(Executable exec) {
		int sign = 0;
		try (SqlSession session = sqlSessionFactory.openSession()) {
			session.commit();
		} catch (Exception e) {
			return 0;
		}
		return sign;
	}

}
