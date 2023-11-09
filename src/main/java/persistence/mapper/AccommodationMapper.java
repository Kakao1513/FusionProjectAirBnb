package persistence.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;
import persistence.dto.AccommodationDTO;

import java.util.List;

public interface AccommodationMapper extends Mapper {
	String getAll = "Select userId, houseName, address, accommodationType ,capacity, accommodationComment, status from accommodation";

	@Select(getAll)
	@Results(
			id = "AccommodationResultSet",
			value = {
					@Result(property = "userID", column = "UserID"),
					@Result(property = "houseName", column = "HouseName"),
					@Result(property = "address", column = "Address"),
					@Result(property = "accommodationType", column = "AccommodationType"),
					@Result(property = "capacity", column = "Capacity"),
					@Result(property = "accommodationComment", column = "AccommodationComment"),
					@Result(property = "status", column = "status"),
			}
	)
	List<AccommodationDTO> getAll();

	@SelectProvider(type = AccommodationSQL.class, method = "selectConfirmed")
	@Results(
			id = "AccommodationConfirmed",
			value = {
					@Result(property = "userID", column = "UserID"),
					@Result(property = "houseName", column = "HouseName"),
					@Result(property = "address", column = "Address"),
					@Result(property = "accommodationType", column = "AccommodationType"),
					@Result(property = "capacity", column = "Capacity"),
					@Result(property = "accommodationComment", column = "AccommodationComment"),
					@Result(property = "status", column = "status"),
			}
	)
	List<AccommodationDTO> getConfirm();
}
