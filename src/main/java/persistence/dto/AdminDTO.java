package persistence.dto;

import java.sql.Date;

public class AdminDTO extends UserDTO
{
    AdminDTO(String name, String phoneNum, Date birth, String accountId, String password, String type)
    {
        super(name, phoneNum, birth, accountId, password, type);
    }
}
