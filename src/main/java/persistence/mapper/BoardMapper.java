package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.BoardDTO;

import java.util.List;

public interface BoardMapper {
	final String getAll = "SELECT * FROM BOARD";

	@Select(getAll)
	@Results(
			id = "boardResultSet",
			value = {
					@Result(property = "id", column = "BOARD_ID"),
					@Result(property = "title", column = "TITLE"),
					@Result(property = "writer", column = "WRITER"),
					@Result(property = "contents", column = "CONTENTS"),
					@Result(property = "regDate", column = "REGDATE"),
					@Result(property = "hit", column = "HIT"),
			}
	)
	List<BoardDTO> getAll();

	@Select("SELECT * FROM BOARD WHERE BOARD_ID=#{id}")
	@ResultMap("boardResultSet")
	BoardDTO selectById(@Param("id") Long id);

	@SelectProvider(type = BoardSQL.class, method = "selectRecent")
	@ResultMap("boardResultSet")
	List<BoardDTO> selectRecentPost(int day);
}
