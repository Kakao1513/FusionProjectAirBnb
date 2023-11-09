package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.UserDTO;

import java.util.List;

public interface UserMapper extends Mapper {
	String getAll = "SELECT name,phone,birth,accountId,password,type FROM USER";
	String getHost = "SELECT name,phone,birth,accountId,password,type FROM USER where type like 'HOST'";

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
	@Select(getHost)
	@ResultMap("UserResultSet")
	List<UserDTO> getHost();




	@SelectProvider(type = UserSQL.class, method = "selectAll")
	@ResultMap("UserResultSet")
	List<UserDTO> selectAll();
}
