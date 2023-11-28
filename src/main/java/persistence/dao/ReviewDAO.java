package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.ReviewDTO;
import persistence.mapper.ReviewMapper;

import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {
    SqlSessionFactory sqlSessionFactory;
    public ReviewDAO(SqlSessionFactory sessionFactory) {
        this.sqlSessionFactory = sessionFactory;
    }
    public List<ReviewDTO> selectReviews(int accomID) {
        List<ReviewDTO> DTOS = new ArrayList<>();

        try (SqlSession session = sqlSessionFactory.openSession()) {
            ReviewMapper reservationMapper = session.getMapper(ReviewMapper.class);
            DTOS = reservationMapper.selectReviews(accomID);
        }catch (Exception e){
            e.printStackTrace();
        }
        return DTOS;
    }
    public void insertReview(ReviewDTO dto) throws Exception{
        try (SqlSession session = sqlSessionFactory.openSession()) {
            ReviewMapper reviewMapper = session.getMapper(ReviewMapper.class);
            reviewMapper.insertReview(dto);
            session.commit();
        }catch (Exception e){
            throw new Exception();
        }
    }
}
