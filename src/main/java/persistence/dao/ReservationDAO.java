package persistence.dao;

import org.apache.ibatis.session.SqlSessionFactory;

public class ReservationDAO {
	SqlSessionFactory sessionFactory;

	public ReservationDAO(SqlSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


}
