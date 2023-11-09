package persistence.dto;

import Enums.UserType;

import java.sql.Date;

public class HostDto extends UserDTO{
	HostDto(String name, String phone, Date birth, String accountId, String password, String type) {
		super(name, phone, birth, accountId, password, type);
	}
}
