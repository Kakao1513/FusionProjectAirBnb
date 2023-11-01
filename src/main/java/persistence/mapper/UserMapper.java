package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.BoardDTO;
import persistence.dto.UserDTO;

import java.util.List;

public interface UserMapper extends Mapper {
	final String getAll = "SELECT * FROM USER";

	@Select(getAll)
	@Results(
			id = "UserResultSet",
			value = {
					@Result(property = "id", column = "userId"),
					@Result(property = "name", column = "Name"),
					@Result(property = "birth", column = "birth"),
					@Result(property = "accountId", column = "AccountID"),
					@Result(property = "password", column = "password"),
					@Result(property = "type", column = "type"),
			}
	)
	List<UserDTO> getAll();

	@SelectProvider(type = UserSQL.class, method = "selectAll")
	@ResultMap("UserResultSet")
	List<UserDTO> selectRecentPost(int day);
}
