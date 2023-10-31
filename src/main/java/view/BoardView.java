package view;

import persistence.dto.BoardDTO;

import java.util.List;

public class BoardView {
	public void printAll(List<BoardDTO> boardDTOS){
		System.out.println("모든 게시글");
		for (BoardDTO boardDTO : boardDTOS) {
			System.out.println("boardDTO = " + boardDTO.toString());
		}
	}
}
