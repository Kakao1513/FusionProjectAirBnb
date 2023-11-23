package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DailyRateDTO;
import persistence.mapper.DailyRateMapper;


public class DailyRateDAO {
    static SqlSessionFactory sqlSessionFactory;

    public DailyRateDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }


    public static DailyRateDTO getDaily(int accomID) {
        DailyRateDTO DTO = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DailyRateMapper dailyrateMapper = session.getMapper(DailyRateMapper.class);
            DTO = dailyrateMapper.getDaily(accomID);
        }
        return DTO;
    }

    public static void setAccomDaily(DailyRateDTO dailyDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DailyRateMapper dailyrateMapper = session.getMapper(DailyRateMapper.class);
            dailyrateMapper.setAccomDaily(dailyDTO);
            session.commit();
        }
    }
}
