package view;

import persistence.dto.ReviewDTO;
import persistence.dto.UserDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReviewView extends View<UserDTO>{


    public void displayReview(List<ReviewDTO> reviewList){
        System.out.println("====================리뷰 리스트====================");
        for(ReviewDTO reviewDTO : reviewList){
            if(reviewDTO.getParentID() == null){
                System.out.printf("[%d][ID:%d](%d) %s\n", reviewDTO.getCommentID(), reviewDTO.getUserID(), reviewDTO.getRate(), reviewDTO.getText());

                for (ReviewDTO reply : getReplies(reviewList, reviewDTO.getCommentID())){
                    System.out.printf("==> [%d][ID:%d] %s\n", reply.getCommentID(), reply.getUserID(), reply.getText());
                }
            }
        }
        System.out.println("=================================================");

    }

    private List<ReviewDTO> getReplies(List<ReviewDTO> reviewList, Integer reviewID){
        return reviewList.stream()
                .filter(ReviewDTO -> Objects.equals(ReviewDTO.getParentID(), reviewID))
                .collect(Collectors.toList());
    }
}
