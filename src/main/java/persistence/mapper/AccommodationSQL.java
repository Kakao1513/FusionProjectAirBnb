package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;

public class AccommodationSQL {

	public String selectConfirmed(){
		SQL sql = new SQL().SELECT("*").FROM("Accommodation").WHERE("STATUS = '승인됨'");
		return sql.toString();
	}
	public String selectAll() {
		SQL sql = new SQL().SELECT("*").FROM("Accommodation");

		return sql.toString();
	}
}
