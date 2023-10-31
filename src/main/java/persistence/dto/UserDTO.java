package persistence.dto;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class UserDTO {
	private String id;
	private String name;
	private String phoneNum;
	private Date birth;
	private String password;

}
