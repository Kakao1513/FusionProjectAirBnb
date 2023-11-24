package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.RatePolicyDTO;
import persistence.mapper.RatePolicyMapper;


public class RatePolicyDAO{
    private final SqlSessionFactory sqlSessionFactory;

    public RatePolicyDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }
    public RatePolicyDTO getRate(int accomID) {
        RatePolicyDTO DTO = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            RatePolicyMapper ratepolicyMapper = session.getMapper(RatePolicyMapper.class);
            DTO = ratepolicyMapper.getRate(accomID);
        }
        return DTO;
    }

    public void setAccomPolicy(RatePolicyDTO rateDTO) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
            RatePolicyMapper ratepolicyMapper = session.getMapper(RatePolicyMapper.class);
            ratepolicyMapper.setAccomPolicy(rateDTO);
            session.commit();
        }
    }

}
