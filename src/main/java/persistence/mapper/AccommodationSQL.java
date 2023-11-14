package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.AccommodationDTO;

public class AccommodationSQL {

	public static String selectByStatus(String status){
		SQL sql = new SQL()
				.SELECT("*")
				.FROM("Accommodation")
				.WHERE("STATUS like #{status}");
		return sql.toString();
	}
	public static String selectAll() {
		SQL sql = new SQL()
				.SELECT("*")
				.FROM("Accommodation");
		return sql.toString();
	}

	public static String getAccom(@Param("accomID") int accomID) {
		SQL sql = new SQL()
				.SELECT("*")
				.FROM("Accommodation")
				.WHERE("accommodationID=#{accomID}");
		return sql.toString();
	}

	public static String insertAccom(@Param("accom") AccommodationDTO accom){
		SQL sql = new SQL()
				.INSERT_INTO("Accommodation")
				.INTO_COLUMNS("userId", "houseName", "address", "accommodationType" ,"capacity", "accommodationComment", "status")
				.INTO_VALUES("#{accom.userID}", "#{accom.accomName}", "#{accom.address}",
						"#{accom.type}", "#{accom.capacity}", "#{accom.comment}","#{accom.status}");
		return sql.toString();
	}
}
