package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.UserDTO;

import java.util.List;

public interface UserMapper extends Mapper {
	String getAll = "SELECT name,phone,birth,accountId,password,type FROM USER";

	@Select(getAll)
	@Results(
			id = "UserResultSet",
			value = {
					@Result(property = "name", column = "Name"),
					@Result(property = "phone", column = "phone"),
					@Result(property = "accountId", column = "AccountID"),
					@Result(property = "birth", column = "birth"),
					@Result(property = "password", column = "password"),
					@Result(property = "type", column = "type"),
			}
	)
	List<UserDTO> getAll();

	@SelectProvider(type = UserSQL.class, method = "selectAll")
	@ResultMap("UserResultSet")
	List<UserDTO> selectAll();
}
