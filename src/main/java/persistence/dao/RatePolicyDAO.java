package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.RatePolicyDTO;
import persistence.mapper.RatePolicyMapper;


public class RatePolicyDAO {
    SqlSessionFactory sqlSessionFactory;

    public RatePolicyDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }
    public RatePolicyDTO getRate(int accomID) {
        RatePolicyDTO DTO = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            RatePolicyMapper reservationMapper = session.getMapper(RatePolicyMapper.class);
            DTO = reservationMapper.getRate(accomID);
        }
        return DTO;
    }
}
