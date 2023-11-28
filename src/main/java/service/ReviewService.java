package service;

import Container.IocContainer;
import persistence.dao.ReviewDAO;
import persistence.dto.AccommodationDTO;
import persistence.dto.ReviewDTO;

import java.util.List;

public class ReviewService {
    ReviewDAO reviewDAO;
    public ReviewService(IocContainer iocContainer){
        reviewDAO = iocContainer.reviewDAO();

    }

    // 13.5 후기
    public List<ReviewDTO> getReviews(AccommodationDTO accomDTO) {
        return reviewDAO.selectReviews(accomDTO.getAccomID());
    }

    // 17. (MyPage)리뷰와 별점 등록
    public void insertReview(ReviewDTO reviewDTO) throws Exception{
        reviewDAO.insertReview(reviewDTO);
    }

}
