package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.DiscountPolicyDTO;
import persistence.mapper.DiscountPolicyMapper;

import java.util.ArrayList;
import java.util.List;


public class DiscountPolicyDAO {
    SqlSessionFactory sqlSessionFactory;

    public DiscountPolicyDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }
    public List<DiscountPolicyDTO> getDiscount(int accomID) {
        List<DiscountPolicyDTO> DTOS = new ArrayList<>();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            DiscountPolicyMapper discountpolicyMapper = session.getMapper(DiscountPolicyMapper.class);
            DTOS = discountpolicyMapper.getDiscount(accomID);
        } catch (Exception e){
            e.printStackTrace();
        }
        return DTOS;
    }

    public int insertDiscount(DiscountPolicyDTO discountDTO) {
        int num = 0;
        try (SqlSession session = sqlSessionFactory.openSession()) {
            DiscountPolicyMapper discountpolicyMapper = session.getMapper(DiscountPolicyMapper.class);
            num = discountpolicyMapper.insertDiscount(discountDTO);
            session.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return num;
    }
}
