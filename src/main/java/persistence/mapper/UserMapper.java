package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.HostDto;
import persistence.dto.UserDTO;

import java.util.List;

public interface UserMapper extends Mapper {
	String getAll = "SELECT name,phone,birth,accountId,password,type FROM USER";
	String getHost = "SELECT name,phone,birth,accountId,password,type FROM USER where type like 'HOST'";

	String getPassword = "Select password from user";

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
	List<HostDto> getHost();

	@SelectProvider(type = UserSQL.class, method = "selectAll")
	@ResultMap("UserResultSet")
	List<UserDTO> selectAll();

	@Select("SELECT password FROM user WHERE AccountID = #{id}")
	String selectPassword(String id);

	@Select("Select name,phone,birth,accountId,password,type from user where accountid= #{id} ")
	UserDTO getUser(String id);


}
