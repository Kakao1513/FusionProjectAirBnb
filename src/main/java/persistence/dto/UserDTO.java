package persistence.dto;

import lombok.*;

import java.sql.Date;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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

	public UserDTO(UserDTO userDTO) {
		userId = userDTO.userId;
		name = userDTO.name;
		phone = userDTO.phone;
		birth = userDTO.birth;
		accountId = userDTO.accountId;
		password = userDTO.password;
		type = userDTO.type;
	}

}
