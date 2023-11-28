package view;

import persistence.dto.AccommodationDTO;
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

    public ReviewDTO getReviewFromUser(UserDTO userDTO, AccommodationDTO accomDTO){
        System.out.println("[후기 등록]");
        System.out.print("별점을 입력하세요 (1~5) : ");
        int rate = readInt();
        System.out.print("내용을 입력하세요 : ");
        String text = SCANNER.nextLine();

        return ReviewDTO.builder()
                .userID(userDTO.getUserId())
                .accomID(accomDTO.getAccomID())
                .rate(rate)
                .text(text)
                .build();
    }

    public ReviewDTO getReplyFromUser(UserDTO userDTO, AccommodationDTO accomDTO, ReviewDTO reviewDTO){
        System.out.println("[답글 등록]");
        System.out.print("답글 내용을 입력하세요 : ");
        String text = SCANNER.nextLine();

        return ReviewDTO.builder()
                .userID(userDTO.getUserId())
                .accomID(accomDTO.getAccomID())
                .parentID(reviewDTO.getParentID())
                .text(text)
                .build();
    }

}
