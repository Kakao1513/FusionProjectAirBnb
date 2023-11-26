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
    public DailyRateDTO getDaily(int accomID) {
        DailyRateDTO DTO = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DailyRateMapper dailyrateMapper = session.getMapper(DailyRateMapper.class);
            DTO = dailyrateMapper.getDaily(accomID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DTO;
    }

    public int setAccomDaily(DailyRateDTO dailyDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DailyRateMapper dailyrateMapper = session.getMapper(DailyRateMapper.class);
            dailyrateMapper.setAccomDaily(dailyDTO);
            session.commit();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
