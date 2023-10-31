package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;

public class BoardSQL {
	public String selectRecent(int day) {
		//builder Style Class
		SQL sql = new SQL()
				.SELECT("*")
				.FROM("BOARD")
				.WHERE("datediff(NOW(), regdate) < #{day}");

		return sql.toString();
	}
}
