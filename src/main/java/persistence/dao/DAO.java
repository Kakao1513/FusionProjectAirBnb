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
		SqlSession session = sqlSessionFactory.openSession();
		try {
			session.commit();
		}
		catch (Exception e) {
			return 0;
		}
		finally {
			session.close();
		}
		return sign;
	}

}
