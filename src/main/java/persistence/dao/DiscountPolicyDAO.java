package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DiscountPolicyDTO;
import persistence.mapper.DiscountPolicyMapper;


public class DiscountPolicyDAO {
    SqlSessionFactory sqlSessionFactory;

    public DiscountPolicyDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }
    public DiscountPolicyDTO getDiscount(int accomID) {
        DiscountPolicyDTO DTO = null;

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DiscountPolicyMapper discountpolicyMapper = session.getMapper(DiscountPolicyMapper.class);
            DTO = discountpolicyMapper.getDiscount(accomID);
        }
        return DTO;
    }
}
