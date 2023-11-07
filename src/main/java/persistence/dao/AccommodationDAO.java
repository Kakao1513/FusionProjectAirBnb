package persistence.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.AccommodationDTO;

public class AccommodationDAO extends DAO<AccommodationDTO> {
	public AccommodationDAO(SqlSessionFactory sqlSessionFactory) {
		super(sqlSessionFactory);
	}

	public void selectAll(){

	}
}
