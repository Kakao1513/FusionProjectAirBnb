package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class UserDTO extends DTO {
	private int userId;
	private String name;
	private String phone;
	private Date birth;
	private String accountId;
	private String password;
	private String type;

	public UserDTO(String id, String pw) {
		accountId = id;
		password = pw;
	}

}
