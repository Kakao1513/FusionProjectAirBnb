package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.Accommodation_amenityDTO;

import java.util.List;
import java.util.Map;

public class Accommodation_amenityDAO extends DAO<Accommodation_amenityDTO> {

    public Accommodation_amenityDAO(SqlSessionFactory sqlSessionFactory) {
        super(sqlSessionFactory);
    }
}

