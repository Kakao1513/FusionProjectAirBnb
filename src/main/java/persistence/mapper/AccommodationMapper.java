package persistence.mapper;

import org.apache.ibatis.annotations.*;
import persistence.dto.AccommodationDTO;

import java.util.List;

public interface AccommodationMapper {
	String getAll = "Select * from accommodation";

	@Select(getAll)
	@Results(
			id = "AccommodationResultSet",
			value = {
					@Result(property = "accommodationId", column = "accommodationId"),
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
	@ResultMap("AccommodationResultSet")
	List<AccommodationDTO> getConfirm();
}
