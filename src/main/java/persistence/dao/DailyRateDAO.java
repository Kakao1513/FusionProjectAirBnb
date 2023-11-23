package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DailyRateDTO;
import persistence.mapper.DailyRateMapper;


public class DailyRateDAO {
    SqlSessionFactory sqlSessionFactory;

    public DailyRateDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }
    public DailyRateDTO getRate(int accomID) {
        DailyRateDTO DTO = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DailyRateMapper dailyrateMapper = session.getMapper(DailyRateMapper.class);
            DTO = dailyrateMapper.getRate(accomID);
        }
        return DTO;
    }

}
