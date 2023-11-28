package service;

import Container.IocContainer;
import lombok.Builder;
import persistence.dao.AccommodationDAO;
import persistence.dao.ReservationDAO;
import persistence.dao.ReviewDAO;
import persistence.dao.UserDAO;
import persistence.dto.ReviewDTO;
import persistence.dto.UserDTO;

import java.sql.Date;
import java.util.Optional;

@Builder
public class UserService {
	private UserDAO userDAO;
	private AccommodationDAO acDAO;
	private ReservationDAO rDAO;
	private ReviewDAO reviewDAO;

	public UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	public UserService(UserDAO userDAO, AccommodationDAO accommodationDAO, ReservationDAO rDAO, ReviewDAO reviewDAO) {
		this.userDAO = userDAO;
		acDAO = accommodationDAO;
		this.rDAO = rDAO;
		this.reviewDAO = reviewDAO;
	}
	public UserService(IocContainer iocContainer){
		this.userDAO = iocContainer.userDAO();
		this.acDAO = iocContainer.accommodationDAO();
		this.rDAO = iocContainer.reservationDAO();
		this.reviewDAO = iocContainer.reviewDAO();
	}



	// 18. 로그인 기능: 호스트, 관리자, 게스트가 로그인 이후 동작하는 것을 보임
	private boolean login(String id, String pw) {
		return pw.equals(userDAO.selectPwById(id));
	}

	public Optional<UserDTO> loginUser(String id, String pw) {
		if (login(id, pw)) {
			return Optional.of(userDAO.selectByAccountId(id));
		} else {
			return Optional.empty();
		}
	}

	// 10. (MyPage)개인정보(이름, 전화번호, 생년월일) 수정
	public UserDTO changePrivacy(UserDTO userDTO, String name, Date birth, String phone) {
		String newName, newPhone;
		Date newBirth;
		if (name.equalsIgnoreCase("x")) {
			newName = userDTO.getName();
		} else {
			newName = name;
		}
		if (birth == null) {
			newBirth = userDTO.getBirth();
		} else {
			newBirth = birth;
		}
		if (phone.equalsIgnoreCase("x")) {
			newPhone = userDTO.getPhone();
		} else {
			newPhone = phone;
		}
		userDTO.setName(newName);
		userDTO.setPhone(newPhone);
		userDTO.setBirth(newBirth);
		userDAO.updateUser(userDTO);
		return userDTO;
	}
	public UserDTO selectByUserID(int id){
		return userDAO.selectById(id);
	}

	// 6. 게스트 리뷰에 대한 답글 등록 : 답글의 parentID를 댓글의 commentID로 설정한 뒤 INSERT

	// 17. (MyPage)리뷰와 별점 등록
	public void insertReview(ReviewDTO reviewDTO) throws Exception{
		reviewDAO.insertReview(reviewDTO);
	}

}
