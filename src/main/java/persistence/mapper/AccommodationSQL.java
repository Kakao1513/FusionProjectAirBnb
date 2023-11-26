package persistence.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.AccommodationDTO;
import persistence.dto.DailyRateDTO;
import persistence.dto.RatePolicyDTO;

public class AccommodationSQL {

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

	public static String insertAccom(@Param("accom") AccommodationDTO accom) {
		SQL sql = new SQL()
				.INSERT_INTO("Accommodation")
				.INTO_COLUMNS("userId", "houseName", "address", "accommodationType", "capacity", "accommodationComment", "status")
				.INTO_VALUES("#{accom.userID}", "#{accom.accomName}", "#{accom.address}",
						"#{accom.type}", "#{accom.capacity}", "#{accom.comment}", "#{accom.status}");
		return sql.toString();
	}

	public static String selectAccom(
			@Param("userID") Integer userID,
			@Param("status") String status,
			@Param("accomName") String accomName,
			@Param("period") String[] period,
			@Param("capacity") String capacity,
			@Param("type") String accomType
	) {
		SQL mainQuery = new SQL()
				.SELECT("*")
				.FROM("Accommodation");
		if (userID != null) {
			mainQuery.WHERE("userID like #{userID}");
		}
		if (status != null) {
			mainQuery.WHERE("status like #{status}");
		}
		if (accomName != null) {
			mainQuery.WHERE("houseName like CONCAT('%', #{accomName}, '%')");
		}
		if (period != null) {
			SQL subSubQuery = new SQL()
					.SELECT("r.RoomID, r.AccommodationID")
					.FROM("reservation r")
					.WHERE("CheckOut >= date(#{period[0]})")
					.WHERE("CheckIn <= date(#{period[1]})");
			SQL subQuery = new SQL()
					.SELECT("accommodationID")
					.FROM("room")
					.WHERE("(RoomID, AccommodationID) NOT IN(" + subSubQuery.toString() + ")");

			mainQuery.WHERE("AccommodationID IN (" + subQuery.toString() + ")");

		}
		if (capacity != null) {
			mainQuery.WHERE("capacity >= #{capacity}");
		}
		if (accomType != null) {
			mainQuery.WHERE("accommodationType like #{accomType}");
		}
		return mainQuery.toString();
	}

	public static String updateStatusById(@Param("accomID") int accomID, @Param("status") String status) {
		SQL sql = new SQL()
				.UPDATE("Accommodation")
				.SET("status = #{status}")
				.WHERE("accommodationID = #{accomID}");
		return sql.toString();
	}

	public static String setAccomPolicy(@Param("rate") RatePolicyDTO rate) {
		SQL sql = new SQL()
				.INSERT_INTO("rate_policy")
				.INTO_COLUMNS("AccommodationID", "weekday", "weekend")
				.INTO_VALUES("#{rate.accomID}, #{rate.weekday}, #{rate.weekend}");
		return sql.toString();
	}

	public static String setAccomDaily(@Param("daily") DailyRateDTO daily) {
		SQL sql = new SQL()
				.INSERT_INTO("daily_rate")
				.INTO_COLUMNS("AccommodationID", "startdate", "enddate", "charge")
				.INTO_VALUES("#{daily.accomID}, #{daily.startDate}, #{daily.endDate}, #{daily.charge}");
		return sql.toString();
	}

	public static String insertRooms(@Param("accomID") int accomID, int roomNum){
		SQL sql = new SQL().INSERT_INTO("room");
		for (int i = 1; i <= roomNum; i++) {
			sql.INTO_VALUES(i+", #{accomID}");
			sql.ADD_ROW();
		}

		return sql.toString();
	}

}
