package org.airbnb;

import Controller.UserController;
import persistence.MyBatisConnectionFactory;
import persistence.dao.UserDAO;
import service.UserService;

import java.sql.SQLException;

public class Main {
	static UserController userController;
	static UserService userService;
	static UserDAO userDAO;

	public static void main(String[] args) throws SQLException {
		userDAO = new UserDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		userService = new UserService(userDAO);
		userController = new UserController(userService);
		userController.login();
		userController.updateUser();
	/*
		MyBoardDAO myBoardDAO = new MyBoardDAO(MyBatisConnectionFactory.getSqlSessionFactory());
		List<BoardDTO> boardDTOS = myBoardDAO.selectAllWithAnnotation();
		List<BoardDTO> boardDTOS = myBoardDAO.selectRecentWithAnnotation(10);
		boardDTOS.stream().forEach(v->{
			System.out.println("v.toString() = " + v.toString());
		});
*/
		//	boardDTOS.stream().forEach(v -> System.out.println("v.toString() = " + v.toString()));
		//의존성 주입은 스프링의 핵심이면서 객체지향의 핵심
	/*	BoardDAO boardDAO = new BoardDAO();
		BoardView boardView = new BoardView();
		BoardService boardService = new BoardService(boardDAO);

		List<BoardDTO> all = boardService.findAll();
		boardView.printAll(all);
	*/
		/*
		InsertBoardDTO insertBoardDTO = InsertBoardDTO.builder()
				.title("mybatis insert")
				.writer("soo")
				.contents("mybatis contents")
				.regDate(LocalDateTime.now())
				.hit(0)
				.build();
		myBoardDAO.insertPost(insertBoardDTO);
		*/


	/*	String title = "test";
		Map params = new HashMap<String, Objects>();
		params.put("title", title);
		List<BoardDTO> posts = myBoardDAO.findPostWithTitleLike(params);
		posts.forEach(v -> System.out.println("v.toString() = " + v.toString()));*/

	}
}
