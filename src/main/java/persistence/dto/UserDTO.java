package persistence.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@Builder
@ToString
public class UserDTO {
	private String id;
	private String name;
	private String phoneNum;
	private Date birth;
	private String accountId;
	private String password;
	private String type;
}
