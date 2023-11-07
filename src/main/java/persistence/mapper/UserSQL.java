package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;

public class UserSQL {

    public String selectAll() {
        SQL sql = new SQL().
                SELECT("*").FROM("User");
        return sql.toString();
    }
}