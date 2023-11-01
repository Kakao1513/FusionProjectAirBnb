package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BoardSQL {
	public String selectAll(int day) {
		//builder Style Class
		SQL sql = new SQL()
				.SELECT("*")
				.FROM("BOARD");
		return sql.toString();
	}
}
