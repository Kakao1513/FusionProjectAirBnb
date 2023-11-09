package persistence.dto;

import java.sql.Date;

public class GuestDTO extends UserDTO{
	GuestDTO(String name, String phone, Date birth, String accountId, String password, String type) {
		super(name, phone, birth, accountId, password, type);
	}
}
