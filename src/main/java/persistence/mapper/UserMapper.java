package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.UserDTO;

import java.util.List;


@Mapper
public interface UserMapper {
	String getAll = "SELECT * FROM USER";
	String getHost = "SELECT * where type like 'HOST'";

	String getPassword = "Select password from user";

	@Select(getAll)
	@Results(
			id = "UserResultSet",
			value = {
					@Result(property = "userId", column = "userid"),
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

	@Select("SELECT password FROM user WHERE AccountID = #{id}")
	String selectPassword(String id);

	@Select("Select * from user where accountid= #{id} ")
	UserDTO selectByAccountId(String id);

	@Select("Select * from user where userid= #{id} ")
	UserDTO selectById(int id);

	@Update("Update user SET name=#{name}, phone=#{phone}, birth=#{birth} WHERE accountid=#{accountId}")
	void updateUser(UserDTO userDTO);

	

}
