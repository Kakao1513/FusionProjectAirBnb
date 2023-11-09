package persistence.mapper;

import org.apache.ibatis.jdbc.SQL;

public class AccommodationSQL {

	public static String selectConfirmed(){
		SQL sql = new SQL().SELECT("userId, houseName, address, accommodationType ,capacity, accommodationComment, status").FROM("Accommodation").WHERE("STATUS like '승인됨'");
		return sql.toString();
	}
	public static String selectAll() {
		SQL sql = new SQL().SELECT("*").FROM("Accommodation");

		return sql.toString();
	}
}
